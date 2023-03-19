package br.com.beautypro.services;

import br.com.beautypro.models.Servicing;
import br.com.beautypro.models.User;
import br.com.beautypro.payload.request.ServicingRequest;
import br.com.beautypro.repository.ServicingRepository;
import br.com.beautypro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicingService {

    @Autowired
    private ServicingRepository servicingRepository;

    @Autowired
    private UserRepository userRepository;

    public Servicing createServicing(ServicingRequest servicingRequest) {
        Servicing servicing = new Servicing();
        servicing.setDescription(servicingRequest.getDescription());
        servicing.setPostService(servicingRequest.getPostService());
        servicing.setPreService(servicingRequest.getPreService());
        servicing.setAverageTime(servicingRequest.getAverageTime());
        servicing.setPrice(servicingRequest.getPrice());
        servicing.setReturnDays(servicingRequest.getReturnDays());
        servicing.setActive(true);


        for (Long userId : servicingRequest.getProfessionalList()) {
            Optional<User> user = userRepository.findById(userId);

            if (user.isPresent()) {
                servicing.getProfessionalList().add(user.get());
            }
        }


        Servicing servicingSaved = servicingRepository.save(servicing);

        List<User> userList = servicingSaved.getProfessionalList();



        return servicingRepository.save(servicing);
    }

    public List<Servicing> listServicing() {
        return servicingRepository.findAll();
    }

    public Servicing getServicing(Long id) {
        return servicingRepository.findById(id).orElse(null);
    }

    public void deleteServicing(Long id) {
        servicingRepository.deleteById(id);
    }



}
