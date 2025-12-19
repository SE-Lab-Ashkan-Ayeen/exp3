# آزمایش سوم - Test-Driven Development (TDD)

## گزارش آزمایش

این پروژه شامل پیاده‌سازی و تست سیستم سبد خرید (Shopping Cart) با استفاده از رویکرد Test-Driven Development است.

---

## بخش اول: کشف خطا

### تحلیل خطا

پس از بررسی کد اولیه، خطای زیر در متد `getTotalWithDiscount()` شناسایی شد:

**خطا:** در خط 29 فایل `ShoppingCart.java`، شرط اعمال تخفیف به صورت `if (total >= 100)` نوشته شده بود، در حالی که طبق مشخصات پروژه، تخفیف باید فقط زمانی اعمال شود که مجموع قیمت‌ها **بیشتر از 100** باشد ، نه برابر یا بیشتر از آن.

**کد مشکل‌دار:**
```java
public double getTotalWithDiscount() {
    double total = getTotal();
    if (total >= 100) {
        return total * 0.9;
    }
    return total;
}
```

**کد اصلاح شده:**
```java
public double getTotalWithDiscount() {
    double total = getTotal();
    if (total > 100) {
        return total * 0.9;
    }
    return total;
}
```

### پاسخ به پرسش اول

تست `testDiscountAtBoundary_WRONG()` انتظار دارد که وقتی مجموع قیمت‌ها دقیقا برابر 100 است، تخفیف 10% اعمال شود و نتیجه 90.0 برگردد. اما طبق مشخصات پروژه، تخفیف باید فقط برای مقادیر **بیشتر از 100** اعمال شود. بنابراین:

1. **خطای منطقی:** کد از `>=` استفاده می‌کرد که باعث می‌شد تخفیف در مجموع 100 نیز اعمال شود، در حالی که باید فقط برای مقادیر بیشتر از 100 اعمال شود.
2. **عدم تطابق با مشخصات:** تست انتظار داشت که در مجموع 100 تخفیف اعمال شود، اما مشخصات پروژه این را مجاز نمی‌داند.

### پاسخ به پرسش دوم

تست زیر برای کشف این خطا نوشته شد:

```java
@Test
public void testDiscountAtBoundary_corrected() {
    ShoppingCart cart = new ShoppingCart();
    cart.addItem("Item1", 50);
    cart.addItem("Item2", 50);

    double discounted = cart.getTotalWithDiscount();

    assertEquals(100.0, discounted);
}
```

این تست مشخص می‌کند که در مجموع دقیقا 100، نباید تخفیف اعمال شود و باید مقدار 100.0 برگردد.

### پاسخ به پرسش سوم

نوشتن آزمون پس از برنامه باعث می‌شود کد با قابلیت تست‌پذیری پایین طراحی شود و برنامه‌نویس دچار سوگیری تایید شده و ناخودآگاه آزمون‌ها را فقط برای اثبات درستی کد (نه یافتن باگ) بنویسد. همچنین این کار باعث می‌شود پوشش موارد لبه‌ای کاهش یافته و هزینه‌ی اصلاح خطاها به دلیل نیاز به بازنویسی بخش‌های اصلی بالا برود.


---

## بخش دوم: به‌کارگیری TDD

### مراحل پیاده‌سازی `updateItemPrice`

#### مرحله RED (قرمز)
سه تست مربوط به `updateItemPrice` از حالت کامنت خارج شدند:
1. `testUpdateItemPrice_ShouldChangePrice()` - بررسی تغییر قیمت
2. `testUpdateItemPrice_ShouldNotChangeCount()` - بررسی عدم تغییر تعداد آیتم‌ها
3. `testUpdateItemPrice_ItemNotFound_ShouldDoNothing()` - بررسی رفتار در صورت عدم وجود آیتم

در این مرحله، تست‌ها به دلیل خالی بودن متد `updateItemPrice` شکست خوردند.

#### مرحله GREEN (سبز)
متد `updateItemPrice` پیاده‌سازی شد:

```java
public void updateItemPrice(String name, int newPrice) {
    if (items.containsKey(name)) {
        items.put(name, (double) newPrice);
    }
}
```

پس از این پیاده‌سازی، تمام تست‌ها پاس شدند.

#### مرحله REFACTOR
کد پیاده‌سازی شده ساده و واضح بود و نیاز به بازسازی خاصی نداشت. با این حال، اطمینان حاصل شد که تمام تست‌های قبلی هنوز پاس می‌شوند.

