import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import AB.BuddyInfo;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * The Class BuddyInfoTest.
 */
public class BuddyInfoTest {
	
	/** The bd 1. */
	private BuddyInfo bd1;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		bd1= new BuddyInfo("Joe", "Carleton", "613-9998888",19);
		new BuddyInfo("Matt", "Guelph", "613-8889999",20);
		new BuddyInfo("John", "Toronto", "613-7774444",13);
	}

	/**
	 * Test copy constructor.
	 */
	@Test
	public void testCopyConstructor() {
		BuddyInfo cb = new BuddyInfo(bd1);
		assertTrue(bd1.equals(cb));
	}
	
	/**
	 * Test greeting.
	 */
	@Test
	public void testGreeting() {
		BuddyInfo cb = new BuddyInfo(bd1);
		assertEquals("Welcome "+cb.getName(),cb.Welcome());
	}
	
	/**
	 * Test age.
	 */
	@Test
	public void testAge() {
		bd1.setAge(16);
		assertEquals(16,bd1.getAge());
	}
	
	/**
	 * Test age over 18.
	 */
	@Test
	public void testAgeOver18() {
		bd1.setAge(25);
		assertTrue(bd1.isOver18());
	}

	/**
	 * Test buddyInfo with JPA.
	 */
	@Test
	public void performJPA(){
		BuddyInfo buddyInfo1 = new BuddyInfo();
		buddyInfo1.setId(1);
		buddyInfo1.setName("Joe");
		buddyInfo1.setAge(34);
		buddyInfo1.setAddress("Ottawa, Ontario");
		buddyInfo1.setPhoneNumber("(613) 123-4567)");

		BuddyInfo buddyInfo2 = new BuddyInfo();
		buddyInfo2.setId(2);
		buddyInfo2.setName("Sean");
		buddyInfo2.setAge(31);
		buddyInfo2.setAddress("Ottawa, Ontario");
		buddyInfo2.setPhoneNumber("(613) 456-7890)");

		BuddyInfo buddyInfo3 = new BuddyInfo();
		buddyInfo3.setId(3);
		buddyInfo3.setName("Brij");
		buddyInfo3.setAge(33);
		buddyInfo3.setAddress("Ottawa, Ontario");
		buddyInfo3.setPhoneNumber("(613) 987-6543)");

		// Connecting to the database through EntityManagerFactory
		// connection details loaded from persistence.xml
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("AddressBookPersistenceUnit");

		EntityManager em = emf.createEntityManager();

		// Creating a new transaction
		EntityTransaction tx = em.getTransaction();

		tx.begin();

		// Persisting the product entity objects
		em.persist(buddyInfo1);
		em.persist(buddyInfo2);
		em.persist(buddyInfo3);

		tx.commit();

		// Querying the contents of the database using JPQL query
		Query q = em.createQuery("SELECT b FROM BuddyInfo b ORDER BY b.id ASC ");

		@SuppressWarnings("unchecked")
		List<BuddyInfo> results = q.getResultList();

		assertEquals(buddyInfo1, results.get(0));
		assertEquals(buddyInfo2, results.get(1));
		assertEquals(buddyInfo3, results.get(2));

		// Closing connection
		em.close();

		emf.close();
	}
}
