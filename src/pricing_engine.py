def calculate_price(price, quantity, customer_type, discount_code):
    subtotal = price * quantity

    # منطق خصم متداخل وغير منظم
    discount = 0
    if customer_type == "VIP":
        discount = subtotal * 0.10

    if discount_code == "SAVE10":
        discount += 10
    elif discount_code == "SAVE20":
        discount += 20

    tax = (subtotal - discount) * 0.15
    return subtotal - discount + tax