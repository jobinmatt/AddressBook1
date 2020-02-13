package AB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AddressBookController {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private BuddyInfoRepository buddyInfoRepository;

    @Autowired
    public AddressBookController(AddressBookRepository repo) {
        this.addressBookRepository = repo;
    }

    @GetMapping("/homepage")
    public String goHome(Model model) {
        model.addAttribute("addressBooks", addressBookRepository.findAll());
        return "homepage";
    }

    @PostMapping("/AddressBook")
    public String createAddressBook(@RequestParam(name = "bookName") String name, Model response) {
        if (!name.equals("")) {
            AddressBook a;
            if (addressBookRepository.findByName(name) != null) {
                a = addressBookRepository.findByName(name);
                System.out.println("AddressBookController:- Found an AddressBook with name: " + name);
            } else {
                a = new AddressBook();
                a.setName(name);
                addressBookRepository.save(a);
                System.out.println("AddressBookController:- Created a new AddressBook with name: " + name);
            }
            return displayAddressBook(a, response);
        } else {
            System.out.println("AddressBookController:- No name given!");
            return goHome(response);
        }
    }

    @GetMapping("/AddressBook")
    public String displayAddressBook(AddressBook book, Model response) {
        System.out.println(book.getBuddyInfoList());
        response.addAttribute("addressBook", book);
        return "showBuddyInfo";
    }

    @PostMapping("/Buddy")
    public String addBuddy(@RequestParam(name = "addressBookName") String addressBookName,
                           @RequestParam(name = "buddyName") String buddyName,
                           @RequestParam(name = "buddyAddress") String buddyAddress,
                           @RequestParam(name = "buddyPhoneNumber") String buddyPhoneNumber,
                           Model response) {
        AddressBook a = addressBookRepository.findByName(addressBookName);
        if (!buddyName.equals("")) {
            if (a.addBuddy(new BuddyInfo(buddyName, buddyAddress, buddyPhoneNumber))) {
                System.out.println("AddressBookController:- Added a buddy with name: " + buddyName);
            } else {
                System.out.println("AddressBookController:- Failed to add a buddy with name: " + buddyName);
            }
            addressBookRepository.save(a);
        } else {
            System.out.println("AddressBookController:- A buddy name was not provided!");
        }
        return displayAddressBook(a, response);
    }

    @PostMapping("/DeleteBuddy")
    public String removeBuddy(@RequestParam(name = "addressBookName") String addressBookName,
                              @RequestParam(name = "buddyName") String buddyName,
                              Model response) {
        AddressBook a = addressBookRepository.findByName(addressBookName);
        if (!buddyName.equals("")) {
            if (a.removeBuddy(buddyName)) {
                System.out.println("AddressBookController:- " + buddyName + " was removed");
            }
            else{
                System.out.println("AddressBookController:- " + buddyName + " was NOT removed!");
            }
            addressBookRepository.save(a);
        } else {
            System.out.println("AddressBookController:- A buddy name was not provided!");
        }
        return displayAddressBook(a, response);
    }
}
