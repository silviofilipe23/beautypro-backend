package br.com.beautypro.services;

import br.com.beautypro.models.User;
import br.com.beautypro.payload.response.PageableResponse;
import br.com.beautypro.services.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public PageableResponse getAllServices(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<br.com.beautypro.models.Service> productResponse = serviceRepository.findAll(pageable);


        List<br.com.beautypro.models.Service> serviceList = productResponse.stream()
                .collect(Collectors.toList());

        PageableResponse response = new PageableResponse();

        response.setData(serviceList);
        response.setPages(productResponse.getTotalPages());
        response.setSize(size);
        response.setTotal(productResponse.getTotalElements());

        return response;
    }

    public PageableResponse getAllServicesFilter(int page, int size, LocalDateTime startDate, LocalDateTime endDate, boolean open) {

        Pageable pageable = PageRequest.of(page, size);
        Page<br.com.beautypro.models.Service> productResponse = serviceRepository.findByDateTimeBetweenAndOpenOrderByDateTimeAsc(startDate, endDate, open, pageable);


        List<br.com.beautypro.models.Service> serviceList = productResponse.stream()
                .collect(Collectors.toList());

        PageableResponse response = new PageableResponse();

        response.setData(serviceList);
        response.setPages(productResponse.getTotalPages());
        response.setSize(size);
        response.setTotal(productResponse.getTotalElements());

        return response;
    }

    public int[] getAppointmentsAvailable(LocalDateTime startDate, LocalDateTime endDate) {

        List<br.com.beautypro.models.Service> appointments = serviceRepository.findByDateTimeBetweenOrderByDateTimeAsc(startDate, endDate);

        int[] array2 = new int[5];

        for (int i = 0; i < appointments.size(); i++) {
            array2[i] = appointments.get(i).getAppointmentTime();
        }

        Set<Integer> set = new HashSet<>();

        int[] array1 = {1, 2, 3, 4, 5};

        for (int item : array1) {
            if (!contemItem(array2, item) && !set.contains(item)) {
                if (item > 0) {
                    set.add(item);
                }
            }
        }

        for (int item : array2) {
            if (!contemItem(array1, item) && !set.contains(item)) {
                if (item > 0) {
                    set.add(item);
                }
            }
        }

        int[] resultant = new int[set.size()];
        int i = 0;
        for (int item : set) {
            resultant[i++] = item;
        }
        return resultant;
    }

    public static boolean contemItem(int[] array, int item) {
        for (int i : array) {
            if (i == item) {
                return true;
            }
        }
        return false;
    }

    public br.com.beautypro.models.Service saveBase64Signature(Long id, String base64Signature) {

        Optional<br.com.beautypro.models.Service> service = serviceRepository.findById(id);

        service.get().setBase64Signature(base64Signature);

        return serviceRepository.save(service.get());
    }

    public List<br.com.beautypro.models.Service> getServiceByUserAndOpenAndDateTimeAfter(User user, boolean open, LocalDateTime dateTime) {
        return serviceRepository.findByUserAndOpenAndDateTimeAfter(user, open, dateTime);
    }


    public br.com.beautypro.models.Service createService(br.com.beautypro.models.Service serviceRequest) {
        return serviceRepository.save(serviceRequest);
    }

    public Optional<br.com.beautypro.models.Service> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public br.com.beautypro.models.Service updateService(br.com.beautypro.models.Service serviceRequest) {
        return serviceRepository.save(serviceRequest);
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}
