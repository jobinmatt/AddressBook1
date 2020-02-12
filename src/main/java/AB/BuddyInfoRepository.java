package AB;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "buddyInfo", path = "buddyInfo")
public interface BuddyInfoRepository extends CrudRepository<BuddyInfo, Integer> {
    void deleteByName(String name);
    BuddyInfo findByName(String name);
    BuddyInfo findById(int id);
}
