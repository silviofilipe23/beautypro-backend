package br.com.beautypro.services;

import br.com.beautypro.services.repository.ServiceRepository;
import br.com.beautypro.services.repository.TermOfConsentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class TermOfConsentService {

    @Autowired
    private TermOfConsentRepository termOfConsentRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    public void save(MultipartFile file, Long idAtendimento) throws IOException {

//        // Salva o arquivo no sistema de arquivos
//        Path caminho = Paths.get("/caminho/para/salvar/os/arquivos");
//        Files.createDirectories(caminho);
//        Path arquivoSalvo = caminho.resolve(arquivo.getOriginalFilename());
//        Files.copy(arquivo.getInputStream(), arquivoSalvo, StandardCopyOption.REPLACE_EXISTING);
//
//        // Cria um objeto Arquivo com as informações do arquivo salvo
//        Arquivo novoArquivo = new Arquivo();
//        novoArquivo.setNome(arquivo.getOriginalFilename());
//        novoArquivo.setCaminho(arquivoSalvo.toString());
//
//        // Salva o arquivo no banco de dados
//        arquivoRepository.save(novoArquivo);
//
//        // Atualiza o atendimento com o arquivo salvo
//        Optional<Atendimento> optionalAtendimento = atendimentoRepository.findById(idAtendimento);
//        if (optionalAtendimento.isPresent()) {
//            Atendimento atendimento = optionalAtendimento.get();
//            atendimento.setArquivo(novoArquivo);
//            atendimentoRepository.save(atendimento);
//        } else {
//            throw new IllegalArgumentException("Atendimento não encontrado");
//        }
    }
}
