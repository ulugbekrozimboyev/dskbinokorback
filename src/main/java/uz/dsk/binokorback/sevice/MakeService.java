package uz.dsk.binokorback.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dsk.binokorback.models.Make;
import uz.dsk.binokorback.repo.MakeRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MakeService {

    @Autowired
    final MakeRepo makeRepo;

    public List<Make> getAll() {
        return makeRepo.findAll();
    }

    public Make save(Make make) {
        return makeRepo.save(make);
    }

    public void remove(String id) {
        Optional<Make> optionalMake = makeRepo.findById(Long.parseLong(id));
        if (optionalMake.isPresent()) {
            Make make = optionalMake.get();
            makeRepo.delete(make);
        }
    }

    public Make getById(String id) {
        Optional<Make> optionalMake = makeRepo.findById(Long.parseLong(id));
        if (optionalMake.isPresent()) {
            Make make = optionalMake.get();
            return make;
        } else {
            return null;
        }
    }

}
