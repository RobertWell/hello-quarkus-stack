#!/usr/bin/env python3

import requests
import base64
import json
import sys
from typing import Dict, Any

class QuarkusApiTester:
    def __init__(self, base_url: str = "http://localhost:8080", username: str = "admin", password: str = "admin"):
        self.base_url = base_url
        self.username = username
        self.password = password
        self.session = requests.Session()
        
        # Set up basic auth
        credentials = base64.b64encode(f"{username}:{password}".encode()).decode()
        self.session.headers.update({
            'Authorization': f'Basic {credentials}',
            'Content-Type': 'application/json'
        })

    def test_endpoint(self, endpoint: str, require_auth: bool = True) -> Dict[str, Any]:
        """Test an endpoint and return results"""
        url = f"{self.base_url}{endpoint}"
        
        try:
            # Test without auth first
            response_no_auth = requests.get(url)
            
            # Test with auth
            if require_auth:
                response_with_auth = self.session.get(url)
            else:
                response_with_auth = response_no_auth
            
            def parse_response(response):
                if response.status_code != 200:
                    return response.text
                
                content_type = response.headers.get('content-type', '')
                if 'application/json' in content_type:
                    return response.json()
                else:
                    # For metrics or other text responses, return first few lines
                    text = response.text
                    lines = text.split('\n')[:5]  # First 5 lines for preview
                    return {'format': 'text', 'preview': lines, 'total_lines': len(text.split('\n'))}
            
            return {
                'endpoint': endpoint,
                'url': url,
                'require_auth': require_auth,
                'no_auth': {
                    'status_code': response_no_auth.status_code,
                    'success': response_no_auth.status_code == 200,
                    'response': parse_response(response_no_auth)
                },
                'with_auth': {
                    'status_code': response_with_auth.status_code,
                    'success': response_with_auth.status_code == 200,
                    'response': parse_response(response_with_auth)
                } if require_auth else None
            }
        except Exception as e:
            return {
                'endpoint': endpoint,
                'url': url,
                'error': str(e)
            }

    def run_tests(self):
        """Run all API tests"""
        print("🧪 Testing Quarkus Basic Auth API")
        print("=" * 50)
        
        # Define test cases
        test_cases = [
            # Protected endpoints (require auth)
            ("/api/introduction", True),
            ("/api/status", True),
            ("/api/info", True),
            
            # Public endpoints (no auth required) - SmallRye Health
            ("/health", False),
            ("/health/ready", False),
            ("/health/live", False),
            # Prometheus metrics
            ("/q/metrics", False),
        ]
        
        results = []
        for endpoint, require_auth in test_cases:
            print(f"\n📍 Testing {endpoint} (Auth required: {require_auth})")
            result = self.test_endpoint(endpoint, require_auth)
            results.append(result)
            
            if 'error' in result:
                print(f"❌ Error: {result['error']}")
                continue
            
            # Check results
            if require_auth:
                # Should fail without auth
                if result['no_auth']['status_code'] == 401:
                    print("✅ Correctly rejected without authentication")
                else:
                    print(f"❌ Expected 401 without auth, got {result['no_auth']['status_code']}")
                
                # Should succeed with auth
                if result['with_auth']['success']:
                    print("✅ Successfully authenticated")
                    print(f"📄 Response: {json.dumps(result['with_auth']['response'], indent=2)}")
                else:
                    print(f"❌ Failed with auth: {result['with_auth']['status_code']}")
            else:
                # Should succeed without auth
                if result['no_auth']['success']:
                    print("✅ Public endpoint accessible without auth")
                    print(f"📄 Response: {json.dumps(result['no_auth']['response'], indent=2)}")
                else:
                    print(f"❌ Public endpoint failed: {result['no_auth']['status_code']}")
        
        # Summary
        print("\n" + "=" * 50)
        print("📊 Test Summary")
        
        protected_tests = [r for r in results if r.get('require_auth')]
        public_tests = [r for r in results if not r.get('require_auth')]
        
        protected_passed = sum(1 for r in protected_tests 
                             if 'error' not in r 
                             and r['no_auth']['status_code'] == 401 
                             and r['with_auth']['success'])
        
        public_passed = sum(1 for r in public_tests 
                          if 'error' not in r 
                          and r['no_auth']['success'])
        
        print(f"Protected endpoints: {protected_passed}/{len(protected_tests)} passed")
        print(f"Public endpoints: {public_passed}/{len(public_tests)} passed")
        
        total_passed = protected_passed + public_passed
        total_tests = len(results)
        print(f"Overall: {total_passed}/{total_tests} tests passed")
        
        return total_passed == total_tests

def main():
    """Main function"""
    import argparse
    
    parser = argparse.ArgumentParser(description='Test Quarkus Basic Auth API')
    parser.add_argument('--url', default='http://localhost:8080', help='Base URL of the API')
    parser.add_argument('--username', default='admin', help='Username for basic auth')
    parser.add_argument('--password', default='admin', help='Password for basic auth')
    
    args = parser.parse_args()
    
    tester = QuarkusApiTester(args.url, args.username, args.password)
    
    try:
        success = tester.run_tests()
        sys.exit(0 if success else 1)
    except KeyboardInterrupt:
        print("\n⏹️  Tests interrupted by user")
        sys.exit(1)
    except Exception as e:
        print(f"\n💥 Unexpected error: {e}")
        sys.exit(1)

if __name__ == "__main__":
    main()