package it.xpug.supermarket.main;

import static org.junit.Assert.*;
import it.xpug.generic.db.*;

import org.junit.*;

public class SupermarketCheckoutRepositoryTest {

	private Database database = new Database(new PropertyFileDatabaseConfiguration("database.properties"));
	CheckoutRepository repository = new CheckoutRepository(database);

	@Test
	public void findsCheckouts() throws Exception {
		assertEquals("id 0", 0, repository.findById(0).id());
		assertEquals("id 1", 1, repository.findById(1).id());
	}

	@Test(expected=CheckoutRepository.CheckoutNotFound.class)
	public void checkoutNotFound() throws Exception {
		repository.findById(99);
	}

	@Test
	public void save() throws Exception {
		SupermarketCheckout checkout = repository.findById(0);
		int totalBefore = checkout.total();
		checkout.scan("A");

		repository.save(checkout);

		SupermarketCheckout reloaded = repository.findById(0);
		assertEquals(totalBefore + 111, reloaded.total());
	}

}
