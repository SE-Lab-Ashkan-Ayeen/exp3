import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {

    @Test
    public void testAddItem() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Book", 50);
        assertEquals(1, cart.getItemCount());
        assertEquals(50.0, cart.getTotal());
    }

    @Test
    public void testRemoveItem() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Pen", 5);
        boolean removed = cart.removeItem("Pen");

        assertTrue(removed);
        assertEquals(0, cart.getItemCount());
        assertEquals(0.0, cart.getTotal());
    }


    @Test
    public void testDiscountAtBoundary_WRONG() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("A", 40);
        cart.addItem("B", 60);

        double discounted = cart.getTotalWithDiscount();

        assertEquals(90.0, discounted);
    }

    @Test
    public void testDiscountAboveThreshold() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Laptop Bag", 120);

        double discounted = cart.getTotalWithDiscount();

        assertEquals(108.0, discounted);
    }

    // Test to discover the discount boundary bug
    @Test
    public void testDiscountAtBoundary_corrected() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Item1", 50);
        cart.addItem("Item2", 50);

        double discounted = cart.getTotalWithDiscount();

        assertEquals(100.0, discounted);
    }

    @Test
    public void testUpdateItemPrice_ShouldChangePrice() {
        ShoppingCart cart = new ShoppingCart();

        cart.addItem("Book", 50);
        cart.updateItemPrice("Book", 80);

        assertEquals(80.0, cart.getTotal()); // باید مجموع تغییر کند
    }

    @Test
    public void testUpdateItemPrice_ShouldNotChangeCount() {
        ShoppingCart cart = new ShoppingCart();

        cart.addItem("Pen", 10);
        cart.updateItemPrice("Pen", 20);

        assertEquals(1, cart.getItemCount());
    }

    @Test
    public void testUpdateItemPrice_ItemNotFound_ShouldDoNothing() {
        ShoppingCart cart = new ShoppingCart();

        cart.addItem("Notebook", 30);
        cart.updateItemPrice("Eraser", 5);

        assertEquals(30.0, cart.getTotal());
    }

    @Test
    public void testUpdateItemPrice_ShouldChangePrice() {
        ShoppingCart cart = new ShoppingCart();

        cart.addItem("Book", 50);
        cart.updateItemPrice("Book", 80);

        assertEquals(80.0, cart.getTotal()); // باید مجموع تغییر کند
    }

    @Test
    public void testUpdateItemPrice_ShouldNotChangeCount() {
        ShoppingCart cart = new ShoppingCart();

        cart.addItem("Pen", 10);
        cart.updateItemPrice("Pen", 20);

        assertEquals(1, cart.getItemCount());
    }

    @Test
    public void testUpdateItemPrice_ItemNotFound_ShouldDoNothing() {
        ShoppingCart cart = new ShoppingCart();

        cart.addItem("Notebook", 30);
        cart.updateItemPrice("Eraser", 5);

        assertEquals(30.0, cart.getTotal());
    }

    // Test empty cart operations
    @Test
    public void testEmptyCart_GetTotal() {
        ShoppingCart cart = new ShoppingCart();
        assertEquals(0.0, cart.getTotal());
    }

    @Test
    public void testEmptyCart_GetItemCount() {
        ShoppingCart cart = new ShoppingCart();
        assertEquals(0, cart.getItemCount());
    }

    @Test
    public void testEmptyCart_RemoveItem() {
        ShoppingCart cart = new ShoppingCart();
        boolean removed = cart.removeItem("NonExistent");
        assertFalse(removed);
        assertEquals(0, cart.getItemCount());
    }

    @Test
    public void testEmptyCart_GetTotalWithDiscount() {
        ShoppingCart cart = new ShoppingCart();
        assertEquals(0.0, cart.getTotalWithDiscount());
    }

    // Test adding item with same name (should overwrite price)
    @Test
    public void testAddItemWithSameName_ShouldOverwrite() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Book", 50);
        cart.addItem("Book", 75);
        assertEquals(1, cart.getItemCount());
        assertEquals(75.0, cart.getTotal());
    }

    // Test discount boundary - just above 100
    @Test
    public void testDiscountJustAbove100() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Item1", 50);
        cart.addItem("Item2", 50.01);
        double discounted = cart.getTotalWithDiscount();
        assertEquals(100.01 * 0.9, discounted, 0.001);
    }

    // Test discount boundary - just below 100
    @Test
    public void testDiscountJustBelow100() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Item1", 50);
        cart.addItem("Item2", 49.99);
        double discounted = cart.getTotalWithDiscount();
        assertEquals(99.99, discounted, 0.001);
    }

    // Test multiple items operations
    @Test
    public void testMultipleItems_RemoveOne() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Item1", 10);
        cart.addItem("Item2", 20);
        cart.addItem("Item3", 30);
        assertEquals(3, cart.getItemCount());
        assertEquals(60.0, cart.getTotal());
        
        boolean removed = cart.removeItem("Item2");
        assertTrue(removed);
        assertEquals(2, cart.getItemCount());
        assertEquals(40.0, cart.getTotal());
    }

    // Test zero price item
    @Test
    public void testZeroPriceItem() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("FreeItem", 0);
        assertEquals(1, cart.getItemCount());
        assertEquals(0.0, cart.getTotal());
    }

    // Test update price to zero
    @Test
    public void testUpdatePriceToZero() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Item", 50);
        cart.updateItemPrice("Item", 0);
        assertEquals(0.0, cart.getTotal());
        assertEquals(1, cart.getItemCount());
    }

    // Test discount with multiple items above threshold
    @Test
    public void testDiscountWithMultipleItems() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Item1", 40);
        cart.addItem("Item2", 35);
        cart.addItem("Item3", 30);
        double discounted = cart.getTotalWithDiscount();
        assertEquals(105.0 * 0.9, discounted);
    }
}   