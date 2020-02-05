package AB;/*
 * Address Book Class
 * @author Jobin Mathew
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * The Class AB.BuddyInfo.
 */
@Entity
public class BuddyInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/** A unique id */
	@Id
	@GeneratedValue
	private int id;

	/** The phoneNumber. */
	private String address, name, phoneNumber;
	
	/** The age. */
	private int age;

	private AddressBook addressBook;

	@ManyToOne
	public AddressBook getAddressBook() {
		return addressBook;
	}

	public void setAddressBook(AddressBook addressBook) {
		this.addressBook = addressBook;
	}



	/**
	 * A no-arg constructor.
	 */
	@Autowired
	public BuddyInfo(){

	}

	/**
	 * Instantiates a new buddy info.
	 *
	 * @param address the address
	 * @param name the name
	 * @param phoneNumber the phoneNumber
	 */
	public BuddyInfo(String name, String address, String phoneNumber) {
		this.setAddress(address);
		this.setName(name);
		this.setPhoneNumber(phoneNumber);
		this.age = -1;
	}
	
	/**
	 * Instantiates a new buddy info.
	 *
	 * @param address the address
	 * @param name the name
	 * @param phoneNumber the phoneNumber
	 * @param age the age
	 */
	public BuddyInfo(String name, String address, String phoneNumber, int age) {
		this.setAddress(address);
		this.setName(name);
		this.setPhoneNumber(phoneNumber);
		this.setAge(age);
	}
	
	/**
	 * Instantiates a new buddy info.
	 *
	 * @param buddyInfo the buddys info
	 */
	//////////////////////////////////////////////////////
	public BuddyInfo(BuddyInfo buddyInfo) {
		this.setAddress(buddyInfo.getAddress());
		this.setName(buddyInfo.getName());
		this.setPhoneNumber(buddyInfo.getPhoneNumber());
		this.setAge(buddyInfo.getAge());
		
	}
	
	/**
	 * Welcome.
	 *
	 * @return the string
	 */
	public String Welcome() {
		return"Welcome "+ getName();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Sets the age.
	 *
	 * @param age the new age
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	/**
	 * Gets the age.
	 *
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * Checks if is over 18.
	 *
	 * @return true, if is over 18
	 */
	public boolean isOver18() {
		if(age>18) return true;
		else return false;
	}
	
	/**
	 * Equals.
	 *
	 * @param b the b
	 * @return true, if successful
	 */
	public boolean equals(BuddyInfo b) {
		if(b.getName().equals(this.name) && b.getAddress().equals(this.address) && b.getPhoneNumber().equals(this.phoneNumber)) {
			return true;
		}
		else return false;
	}
	
	/**
	 * Import from string.
	 *
	 * @param s the s
	 * @return the buddy info
	 */
	public static BuddyInfo importFromString(String s){
		String[] word = s.split("\\$");
		BuddyInfo buddy = new BuddyInfo(word[0], word[1], word[2]);
		return buddy;
	}
	////////////////////////////////////////////////////////
	
	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the phoneNumber.
	 *
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * Sets the phoneNumber.
	 *
	 * @param phoneNumber the new phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return name+"$"+address+"$"+ phoneNumber;
	}
	
	public String toXML(){
		String s = "";
		s = "\t<AB.BuddyInfo>" +
				"\n\t\t<id>" + Integer.toString(id) + "</id>" +
				"\n\t\t<name>" + name + "</name>" +
				"\n\t\t<address>" + address + "</address>" +
				"\n\t\t<phoneNumber>" + phoneNumber + "</phoneNumber>" +
				"\n\t\t<age>" + Integer.toString(age) + "</age>" +
				"\n\t</AB.BuddyInfo>\n";
		return s;
	}
}