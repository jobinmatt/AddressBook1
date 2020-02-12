package AB;/*
 * Model Class for the address book.
 * @author Jobin Mathew
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

// TODO: Auto-generated Javadoc

/**
 * The Class AB.AddressBook.
 */
@Component
@Entity
public class AddressBook implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    private String name;

    /**
     * The buddy info list.
     */
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Collection<BuddyInfo> buddyInfoList;

    /**
     * Instantiates a new address book.
     */
    @Autowired
    public AddressBook() {
        this.buddyInfoList = new ArrayList<BuddyInfo>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Collection<BuddyInfo> getBuddyInfoList() {
        return buddyInfoList;
    }

    public void setBuddyInfoList(Collection<BuddyInfo> buddyInfoList) {
        this.buddyInfoList = (ArrayList<BuddyInfo>) buddyInfoList;
    }

    /**
     * Size.
     *
     * @return the int
     */
    ////////////////////////////////////////////////////
    public int size() {
        return buddyInfoList.size();
    }

    /**
     * Clear.
     */
    public void clear() {
        buddyInfoList.clear();
        System.out.println("AddressBook:- Cleared all buddies!");
    }
    ////////////////////////////////////////////////////

    /**
     * Adds the buddy.
     *
     * @param buddy the buddy
     * @return true, if successful
     */
    public boolean addBuddy(BuddyInfo buddy) {
        if (buddy != null) {
            for (BuddyInfo testbuddy : this.buddyInfoList) {
                if (buddy.equals(testbuddy)) {
                    System.out.println("AddressBook:- Same Buddies!");
                    return false;
                }
            }
            this.buddyInfoList.add(buddy);
            System.out.println("AddressBook:- Added a Buddy!");
            System.out.println("AddressBook:- " + buddy.Welcome());
        }
        return true;
    }

    /**
     * Edits the buddy.
     *
     * @param index the index
     * @param buddy the buddy
     */
    public void editBuddy(int index, BuddyInfo buddy) {
        if (index >= 0) {
            ((ArrayList) this.buddyInfoList).set(index, buddy);
            System.out.println("AddressBook:- Edited a Buddy!");
        }
    }

    /**
     * Removes the buddy.
     *
     * @param buddy
     * @return boolean true or false based on if buddy was removed
     */
    public boolean removeBuddy(String buddy) {
        BuddyInfo temp = null;
        for (BuddyInfo bud : buddyInfoList) {
            if (bud.getName().equals(buddy)) {
                temp = bud;
                break;
            }
        }
        if (temp == null) {
            System.out.println("AddressBook:- " + buddy + " not found!");
        }
        return this.buddyInfoList.remove(temp);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String output = "";
        for (BuddyInfo infobuddy : buddyInfoList) {
            output += infobuddy.toString() + "\n";
        }
        return output;
    }

    /**
     * Export.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void export() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("AB.AddressBook.txt"));
        for (BuddyInfo buddy : buddyInfoList) {
            String s = buddy.toString();
            System.out.println(s);
            out.write(s);
            out.newLine();
        }
        out.close();
    }

    /**
     * Import from file.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void importFromFile(File selectedFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(selectedFile));
        try {
            String line = br.readLine();
            while (line != null) {
                System.out.println("AddressBook:- " + line);
                this.addBuddy(BuddyInfo.importFromString(line));
                line = br.readLine();
            }
        } finally {
            br.close();
        }
    }

    /**
     * Serialized export.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void serializedExport() throws IOException {
        FileOutputStream out = new FileOutputStream("SerializedAddressBook.txt");
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(this);
        oos.close();
        out.close();
    }

    /**
     * Serialized import.
     *
     * @return the address book
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public AddressBook serializedImport() throws IOException {
        FileInputStream input = new FileInputStream("SerializedAddressBook.txt");
        ObjectInputStream ois = new ObjectInputStream(input);
        AddressBook book = new AddressBook();
        try {
            book = (AddressBook) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ois.close();
        input.close();
        return book;
    }

    /**
     * Equals.
     *
     * @param book the book
     * @return true, if successful
     */
    public boolean equals(AddressBook book) {
        if (this.toString().equals(book.toString()))
            return true;
        else
            return false;
    }

    public String toXML() {
        String s = "";
        String b = "";
        for (BuddyInfo buddy : this.buddyInfoList) {
            b += buddy.toXML();
        }
        s = "<AB.AddressBook>\n" + b + "</AB.AddressBook>\n";
        return s;
    }

    public void exportToXML() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("AB.AddressBook.xml"));
        String s = this.toXML();
        System.out.println(s);
        out.write(s);
        out.newLine();

        out.close();
    }

    public static String indentXMLString(String input, int indent) {
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String indentXMLString(String input) {
        return indentXMLString(input, 4);
    }


    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();
        BuddyInfo buddyInfo1 = new BuddyInfo("Rajat", "Canada", "613-435-1111");
        BuddyInfo buddyInfo2 = new BuddyInfo("Sean", "Canada", "613-435-2222");
        BuddyInfo buddyInfo3 = new BuddyInfo("Joe", "Canada", "613-435-3333");
        BuddyInfo buddyInfo4 = new BuddyInfo("Matt", "Canada", "613-435-4444");
        addressBook.addBuddy(buddyInfo1);
        addressBook.addBuddy(buddyInfo2);
        addressBook.addBuddy(buddyInfo3);
        addressBook.addBuddy(buddyInfo4);
        System.out.println("AddressBook:- " + addressBook.toString());
    }
}