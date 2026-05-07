import unittest
from src.pricing_engine import calculate_price

class TestPricing(unittest.TestCase):
    def test_regular_customer(self):
        # 100 * 2 = 200 -> ضريبة 15% = 30 -> المجموع 230
        result = calculate_price(100, 2, "REGULAR", "NONE")
        self.assertEqual(result, 230.0)

if __name__ == '__main__':
    unittest.main()