package br.com.beautypro.services;

import br.com.beautypro.models.Client;
import br.com.beautypro.models.EPaymentType;
import br.com.beautypro.models.Servicing;
import br.com.beautypro.models.User;
import br.com.beautypro.payload.request.*;
import br.com.beautypro.payload.response.PageableResponse;
import br.com.beautypro.services.repository.ServiceRepository;
import br.com.beautypro.services.repository.ServicingRepository;
import br.com.beautypro.util.EmailUtil;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceService {

    @Autowired
    private final ServiceRepository serviceRepository;

    @Autowired
    private final ServicingRepository servicingRepository;

    @Autowired
    private final EmailUtil emailUtil;

    public ServiceService(ServiceRepository serviceRepository, ServicingRepository servicingRepository, EmailUtil emailUtil) {
        this.serviceRepository = serviceRepository;
        this.servicingRepository = servicingRepository;
        this.emailUtil = emailUtil;
    }

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

    public PageableResponse getAllServicesFilterDesc(
            int page, int size, LocalDateTime startDate, LocalDateTime endDate,
            String paymentType, String clientName, String serviceName, String professionalName) {

        // Pageable com ordenação por data de criação descendente
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        // Especificação para aplicar os filtros
        Specification<br.com.beautypro.models.Service> specification = Specification.where(null);

        if (startDate != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("dateTime"), startDate));
        }

        if (endDate != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("dateTime"), endDate));
        }

        if (paymentType != null && !paymentType.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("paymentType"), EPaymentType.valueOf(paymentType)));
        }

        if (clientName != null && !clientName.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("client").get("name")),
                            "%" + clientName.toLowerCase() + "%"));
        }

        if (serviceName != null && !serviceName.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("servicing").get("description")),
                            "%" + serviceName.toLowerCase() + "%"));
        }

        if (professionalName != null && !professionalName.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("user").get("name")),
                            "%" + professionalName.toLowerCase() + "%"));
        }

        Page<br.com.beautypro.models.Service> productResponse = serviceRepository.findAll(specification, pageable);


        List<br.com.beautypro.models.Service> serviceList = productResponse.stream()
                .collect(Collectors.toList());

        PageableResponse response = new PageableResponse();

        response.setData(serviceList);
        response.setPages(productResponse.getTotalPages());
        response.setSize(size);
        response.setTotal(productResponse.getTotalElements());

        return response;
    }

    private List<StatusCount> getStatusCounts(List<br.com.beautypro.models.Service> services) {
        long openedCount = services.stream().filter(br.com.beautypro.models.Service::isOpen).count();
        long canceledCount = services.stream().filter(service -> !service.isOpen() && service.getAppointmentTime() == 0 && service.getFinishedDate() == null).count();
        long finishedCount = services.stream().filter(service -> !service.isOpen() && service.getFinishedDate() != null).count();


        return Arrays.asList(
                new StatusCount("ABERTO", openedCount),
                new StatusCount("CANCELADO", canceledCount),
                new StatusCount("FINALIZADO", finishedCount)
        );
    }

    public List<StatusCount> getStatusCountsInCurrentMonth() {
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfMonth = LocalDateTime.now().withDayOfMonth(LocalDateTime.now().toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        List<br.com.beautypro.models.Service> servicesInMonth = serviceRepository.findByDateTimeBetween(startOfMonth, endOfMonth);

        return getStatusCounts(servicesInMonth);
    }

    public List<ServicingCountDTO> getServicingCountsInCurrentMonth() {
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfMonth = LocalDateTime.now().withDayOfMonth(LocalDateTime.now().toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        List<br.com.beautypro.models.Service> servicesInMonth = serviceRepository.findByDateTimeBetweenAndFinishedDateIsNotNull(startOfMonth, endOfMonth);

        // Agrupa os serviços por procedimento e conta a quantidade de ocorrências
        Map<Long, Long> servicingCounts = servicesInMonth.stream()
                .collect(Collectors.groupingBy(service -> service.getServicing().getId(), Collectors.counting()));

        // Recupera os procedimentos associados aos serviços do mês atual
        List<Servicing> servicingsInMonth = servicingRepository.findAllById(servicingCounts.keySet());

        // Converte os resultados para a classe DTO
        return servicingsInMonth.stream()
                .map(servicing -> new ServicingCountDTO(servicing.getId(), servicing.getDescription(), servicingCounts.getOrDefault(servicing.getId(), 0L)))
                .collect(Collectors.toList());
    }

    public List<MonthlyServiceSumDTO> getMonthlyServiceSums() {
        List<Object[]> monthlySums = serviceRepository.getMonthlyServiceSums();

        // Mapeia os resultados da consulta para a classe DTO
        return monthlySums.stream()
                .map(row -> {
                    YearMonth yearMonth = YearMonth.of((int) row[0], (int) row[1]);
                    double totalSum = (double) row[2];
                    return new MonthlyServiceSumDTO(yearMonth.getMonthValue(), yearMonth.getYear(), totalSum);
                })
                .collect(Collectors.toList());
    }

    public List<DailyServiceCountDTO> getDailyServiceCountsInCurrentMonth() {
        List<Object[]> dailyServiceCounts = serviceRepository.getDailyServiceCountsInCurrentMonth();

        // Mapeia os resultados da consulta para a classe DTO
        return dailyServiceCounts.stream()
                .map(row -> {
                    int day = (int) row[0];
                    long count = (long) row[1];
                    return new DailyServiceCountDTO(day, count);
                })
                .collect(Collectors.toList());
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
            if (contemItem(array2, item) && !set.contains(item)) {
                if (item > 0) {
                    set.add(item);
                }
            }
        }

        for (int item : array2) {
            if (contemItem(array1, item) && !set.contains(item)) {
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
                return false;
            }
        }
        return true;
    }

    public PageableResponse listServicesByClients(int page, int size, Client client) {
        Pageable pageable = PageRequest.of(page, size);
        Page<br.com.beautypro.models.Service> services = serviceRepository.findByClient(client, pageable);
        List<br.com.beautypro.models.Service> serviceList = services.stream()
                .collect(Collectors.toList());

        PageableResponse response = new PageableResponse();

        response.setData(serviceList);
        response.setPages(services.getTotalPages());
        response.setSize(size);
        response.setTotal(services.getTotalElements());
        return response;
    }

    public br.com.beautypro.models.Service saveBase64Signature(Long id, String base64Signature) {

        Optional<br.com.beautypro.models.Service> service = serviceRepository.findById(id);

        service.get().setBase64Signature(base64Signature);

        return serviceRepository.save(service.get());
    }

    public List<br.com.beautypro.models.Service> getServiceByUserAndOpenAndDateTimeAfter(User user, boolean open, LocalDateTime dateTime) {
        return serviceRepository.findByUserAndOpenAndDateTimeAfter(user, open, dateTime);
    }


    public br.com.beautypro.models.Service createService(br.com.beautypro.models.Service serviceRequest) throws MessagingException {

        String subject = "Agendamento Larissa Dionizio PMU";
        String body = "Olá "+ serviceRequest.getClient().getName()  + "."
                + " Seu procedimento " + serviceRequest.getServicing().getDescription()
                + " foi agendado para o dia " + this.getDate(serviceRequest.getDateHour())
                + " às " + this.getHour(serviceRequest.getDateHour().minusHours(3))  + "."
                + " Esperamos por você!";

        emailUtil.sendEmail(serviceRequest.getClient().getEmail(), subject, body);

//        Message message = Message.creator(
//                        new com.twilio.type.PhoneNumber("whatsapp:+55" + serviceRequest.getClient().getPhoneNumber().substring(0, 2) + serviceRequest.getClient().getPhoneNumber().substring(3)),
//                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
//                        body)
//                .create();

        return serviceRepository.save(serviceRequest);
    }

    public Optional<br.com.beautypro.models.Service> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public br.com.beautypro.models.Service updateService(br.com.beautypro.models.Service serviceRequest) throws MessagingException {





        String subject = "Agendamento Larissa Dionizio PMU";
        String body = "Olá "+ serviceRequest.getClient().getName()  + "."
                + " Seu procedimento " + serviceRequest.getServicing().getDescription()
                + " foi reagendado para o dia " + this.getDate(serviceRequest.getDateHour())
                + " às " + this.getHour(serviceRequest.getDateHour().minusHours(3))  + "."
                + " Esperamos por você!";

        emailUtil.sendEmail(serviceRequest.getClient().getEmail(), subject, body);

        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:+55" + serviceRequest.getClient().getPhoneNumber().substring(0, 2) + serviceRequest.getClient().getPhoneNumber().substring(3)),
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                        body)
                .create();


        return serviceRepository.save(serviceRequest);
    }

    public String getDate(LocalDateTime dateTime) {

        ZoneId zoneId = ZoneId.of("GMT-3");
        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, zoneId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = zonedDateTime.format(formatter);

        return dataFormatada;
    }

    public String getHour(LocalDateTime dateTime) {

        ZoneId zoneId = ZoneId.of("GMT-3");
        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, zoneId);

        // Formata a ZonedDateTime de acordo com o padrão desejado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String horaFormatada = zonedDateTime.format(formatter);

        return horaFormatada;
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}
