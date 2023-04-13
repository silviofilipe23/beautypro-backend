package br.com.beautypro.services.repository;

import br.com.beautypro.models.Supplier;
import br.com.beautypro.models.TermOfConsent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermOfConsentRepository extends JpaRepository<TermOfConsent, Long> {
}
