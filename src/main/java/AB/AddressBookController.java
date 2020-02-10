package AB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AddressBookController {

    private AddressBookRepository addressBookRepository;

    @Autowired
    public AddressBookController(AddressBookRepository repo) {
        this.addressBookRepository = repo;
    }

    @GetMapping("/homepage")
    public String goHome(Model model) {
        model.addAttribute("addressBook", new AddressBook());
        return "index";
    }

    @PostMapping("/AddressBook")
    public String createAddressBook(@RequestParam(name = "name") String name, Model response) {
        AddressBook a = new AddressBook();
        a.setName(name);
        addressBookRepository.save(a);
        return displayAddressBook(name, response);
    }

    @GetMapping("/AddressBook")
    public String displayAddressBook(@RequestParam(name = "name") String name, Model response) {
        AddressBook a = addressBookRepository.findByName(name);
        response.addAttribute("addressBook", a);
        response.addAttribute("buddyInfo", new BuddyInfo());
        return "showBuddyInfo";
    }

    @PostMapping("/Buddy")
    public String addBuddy(@RequestParam(name = "addressBookName") String addressBookName, @ModelAttribute BuddyInfo buddyInfo, Model response) {
        AddressBook a = addressBookRepository.findByName(addressBookName);
        a.addBuddy(buddyInfo);
        addressBookRepository.save(a);
        return displayAddressBook(addressBookName, response);
    }

    @DeleteMapping("/Buddy")
    public String removeBuddy(@RequestParam(name = "addressBookName") String addressBookName, @ModelAttribute String buddyName, Model response) {
        AddressBook a = addressBookRepository.findByName(addressBookName);
        a.removeBuddy(buddyName);
        addressBookRepository.save(a);
        return displayAddressBook(addressBookName, response);
    }
}
