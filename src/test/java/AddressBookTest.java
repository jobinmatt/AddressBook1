import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

import AB.AddressBook;
import AB.AddressBookRepository;
import AB.BuddyInfo;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.repository.CrudRepository;

// TODO: Auto-generated Javadoc

/**
 * The Class AddressBookTest.
 */
public class AddressBookTest {

    /**
     * The book 1.
     */
    private AddressBook book1;

    /**
     * The bd 1.
     */
    private BuddyInfo bd1;

    /**
     * The bd 2.
     */
    private BuddyInfo bd2;

    /**
     * The bd 3.
     */
    private BuddyInfo bd3;

    /**
     * The bd 01.
     */
    private BuddyInfo bd01;

    /**
     * The bd 02.
     */
    private BuddyInfo bd02;

    /**
     * The bd 03.
     */
    private BuddyInfo bd03;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        book1 = new AddressBook();
        bd1 = new BuddyInfo("Joe", "Carleton", "613-9998888", 19);
        bd2 = new BuddyInfo("Matt", "Guelph", "613-8889999", 20);
        bd3 = new BuddyInfo("John", "Toronto", "613-7774444", 13);
        // Without age
        bd01 = new BuddyInfo("Joe", "Carleton", "613-9998888");
        bd02 = new BuddyInfo("Matt", "Guelph", "613-8889999");
        bd03 = new BuddyInfo("John", "Toronto", "613-7774444");
    }

    /**
     * Test size.
     */
    @Test
    public void testSize() {
        System.out.println("\n**** Testing Size ****");
        book1.addBuddy(bd1);
        book1.addBuddy(bd2);
        book1.addBuddy(bd3);
        assertEquals(3, book1.size());
        System.out.println(book1.size() == 3 ? "Success! Passed the sizeTest!" : "Failure! Failed the sizeTest!");
    }

    /**
     * Test clear.
     */
    @Test
    public void testClear() {
        System.out.println("\n**** Testing Clear ****");
        book1.addBuddy(bd1);
        book1.addBuddy(bd2);
        book1.addBuddy(bd3);
        book1.clear();
        assertEquals(0, book1.size());
        System.out.println(book1.size() == 0 ? "Success! Passed the clearTest!" : "Failure! Failed the clearTest!");
    }

    /**
     * Test addressBook with JPA.
     */
    @Test
    public void performJPA() {
        AddressBook addressBook1 = new AddressBook();

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

        addressBook1.addBuddy(buddyInfo1);
        addressBook1.addBuddy(buddyInfo2);
        addressBook1.addBuddy(buddyInfo3);

        // Connecting to the database through EntityManagerFactory
        // connection details loaded from persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AddressBookPersistenceUnit");

        EntityManager em = emf.createEntityManager();

        // Creating a new transaction
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        // Persisting the product entity objects
        em.persist(addressBook1);

        tx.commit();

        // Querying the contents of the database using JPQL query
        Query q = em.createQuery("SELECT a FROM AddressBook a ORDER BY a.id ASC");

        @SuppressWarnings("unchecked")
        List<AddressBook> results = q.getResultList();

        ArrayList<BuddyInfo> buddyInfos = (ArrayList<BuddyInfo>) results.get(0).getBuddyInfoList();
        assertEquals(buddyInfo1, buddyInfos.get(0));
        assertEquals(buddyInfo2, buddyInfos.get(1));
        assertEquals(buddyInfo3, buddyInfos.get(2));

        // Closing connection
        em.close();

        emf.close();
    }

}
