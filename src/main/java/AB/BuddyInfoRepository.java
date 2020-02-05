package AB;

import org.springframework.data.repository.CrudRepository;


public interface BuddyInfoRepository extends CrudRepository<BuddyInfo, Integer> {
    void deleteByName(String name);
    BuddyInfo findByName(String name);
    BuddyInfo findById(int id);
}
