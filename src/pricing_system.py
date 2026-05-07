class DiscountManager:
    """مسؤول عن حساب الخصومات فقط"""

    def __init__(self):
        self.codes = {"SAVE10": 10, "SAVE20": 20}

    def get_discount(self, subtotal, customer_type, code):
        discount = 0
        if customer_type == "VIP":
            discount += subtotal * 0.10  # خصم الـ VIP

        discount += self.codes.get(code, 0)  # خصم الكود إن وجد
        return discount


class TaxCalculator:
    """مسؤول عن حساب الضرائب فقط"""
    TAX_RATE = 0.15

    def apply_tax(self, amount):
        return amount * self.TAX_RATE


class PricingEngine:
    """المحرك الأساسي الذي يربط القطع ببعضها"""

    def __init__(self):
        self.discounts = DiscountManager()
        self.taxes = TaxCalculator()

    def calculate_final_price(self, price, quantity, customer_type, discount_code):
        subtotal = price * quantity
        discount = self.discounts.get_discount(subtotal, customer_type, discount_code)

        taxable_amount = subtotal - discount
        tax = self.taxes.apply_tax(taxable_amount)

        return taxable_amount + tax