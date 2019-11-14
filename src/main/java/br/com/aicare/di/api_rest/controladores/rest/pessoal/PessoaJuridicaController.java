/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.pessoal;

import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.localizacao.Cidade;
import br.com.aicare.di.api_rest.dominio.localizacao.Email;
import br.com.aicare.di.api_rest.dominio.localizacao.Endereco;
import br.com.aicare.di.api_rest.dominio.localizacao.Telefone;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaJuridica;
import br.com.aicare.di.api_rest.dto.pessoal.PessoaJuridicaDTO;
import br.com.aicare.di.api_rest.repository.localizacao.CidadeRepository;
import br.com.aicare.di.api_rest.repository.localizacao.EmailRepository;
import br.com.aicare.di.api_rest.repository.localizacao.TelefoneRepository;
import br.com.aicare.di.api_rest.repository.pessoal.PessoaJuridicaRepository;
import br.com.aicare.di.api_rest.uteis.CNPJ;
import br.com.aicare.di.api_rest.uteis.NumeroTelefone;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import br.com.aicare.di.api_rest.dto.pessoal.PessoaJuridicaSimpleResponseDTO;
import org.springframework.security.access.annotation.Secured;

/**
 *
 * @author Paulo Collares
 */
@RestController
@CrossOrigin
@Secured({"ROLE_ADA", "ROLE_ADM", "ROLE_ADH"})
@RequestMapping("/pessoal/pessoas-juridicas")
@Api(tags = "Pessoa Jurídica", description = "Pessoas Jurídicas")
public class PessoaJuridicaController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    TelefoneRepository telefoneRepository;

    @Autowired
    EmailRepository emailRepository;

    @ApiOperation(value = "Lista as pessoas juridicas ou Busca pelo CNPJ, Nome, Nome fantasia ou inscrição estadual")
    @GetMapping()
    public Page<PessoaJuridica> listar(
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size,
            @RequestParam(
                    value = "sort",
                    required = false) String sort,
            @RequestParam(
                    value = "q",
                    required = false) String q
    ) {
        Pageable pageable = new PageableFactory(page, size, sort).getPageable();

        Page<PessoaJuridica> resultPage;

        if (q == null) {
            resultPage = pessoaJuridicaRepository.findAll(pageable);
        } else {
            resultPage = pessoaJuridicaRepository.busca(q.toLowerCase(), pageable);
        }

        return resultPage;
    }

    @ApiOperation(value = "Lista as pessoas juridicas com informações reduzidas")
    @GetMapping("/all")
    public Collection<PessoaJuridicaSimpleResponseDTO> listar() {
        Collection<PessoaJuridicaSimpleResponseDTO> all = pessoaJuridicaRepository.all();
        return all;
    }

    @ApiOperation(value = "Lista uma pessoa juridica pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PessoaJuridica> listar(@PathVariable Integer id) {
        Optional<PessoaJuridica> pessoa = pessoaJuridicaRepository.findById(id);

        if (!pessoa.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(pessoa.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Cria uma nova pessoa juridica")
    @PostMapping()
    public ResponseEntity<PessoaJuridica> criar(@RequestBody PessoaJuridicaDTO dto, UriComponentsBuilder ucBuilder) {
        try {
            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            PessoaJuridica pessoaJuridica = new PessoaJuridica();

            pessoaJuridica.setInscricaoEstadual(dto.getInscricaoEstadual());
            pessoaJuridica.setNomeFantasia(dto.getNomeFantasia());

            if (dto.getNome() == null || dto.getNome().length() < 3) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_nome"));
            }
            pessoaJuridica.setNome(dto.getNome());

            CNPJ cnpj = new CNPJ(dto.getCnpj());
            if (!cnpj.isCNPJ()) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_cnpj_invalido"));
            }
            if (pessoaJuridicaRepository.cnpjExistente(dto.getCnpj())) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_cnpj_ja_cadastrado"));
            }
            pessoaJuridica.setCnpj(dto.getCnpj());

            List<Email> emails = new ArrayList<>();
            if (dto.getEmails() != null && dto.getEmails().size() > 0) {
                for (String email : dto.getEmails()) {
                    if (!email.isEmpty()) {
                        if (!new br.com.aicare.di.api_rest.uteis.Email(email).isValido()) {
                            return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_email_invalido") + ": " + email);
                        }
                        Email e = new Email();
                        e.setEmail(email);
                        emails.add(e);
                    }
                }
            }

            List<Telefone> telefones = new ArrayList<>();
            if (dto.getTelefones() == null || dto.getTelefones().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_telefone"));
            }
            for (String tel : dto.getTelefones()) {
                NumeroTelefone validadorTelefone = new NumeroTelefone(tel);
                if (!tel.isEmpty()) {
                    if (!validadorTelefone.isValido()) {
                        return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_telefone_invalido") + ": " + tel);
                    }
                    Telefone telefone = new Telefone();
                    telefone.setNumero(tel);
                    telefones.add(telefone);
                }
            }
            if (telefones.size() < 1) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_telefone"));
            }

            if (dto.getCidade() == null) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_cidade"));
            }
            Optional<Cidade> cidade = cidadeRepository.findById(dto.getCidade());
            if (!cidade.isPresent()) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_cidade_invalida"));
            }
            Endereco endereco = new Endereco();
            endereco.setCidade(cidade.get());

            if (dto.getBairro() == null || dto.getBairro().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_bairro"));
            }
            endereco.setBairro(dto.getBairro());

            if (dto.getCep() == null || dto.getCep().length() != 8) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_cep"));
            }
            endereco.setCep(dto.getCep());

            if (dto.getLogradouro() == null || dto.getLogradouro().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_logradouro"));
            }
            endereco.setLogradouro(dto.getLogradouro());

            //Salvo o objeto utilizando o repositório
            pessoaJuridica.setTelefones(new HashSet<>(telefones));
            pessoaJuridica.setEmails(new HashSet<>(emails));
            pessoaJuridica.setEndereco(endereco);
            PessoaJuridica novo = pessoaJuridicaRepository.save(pessoaJuridica);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null) {
                return ApiError.badRequest("Ocorreu algum erro na criação desse objeto");
            }

            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao criar uma pessoa juridica", e);
            return ApiError.internalServerError(Translator.toLocale("erro_criacao"));
        }
    }

    @ApiOperation(value = "Atualiza uma pessoa juridica")
    @PutMapping(value = "/{id}")
    public ResponseEntity<PessoaJuridica> atualizar(@PathVariable("id") int id, @RequestBody PessoaJuridicaDTO dto) {
        try {
            Optional<PessoaJuridica> pessoaAtual = pessoaJuridicaRepository.findById(id);

            if (!pessoaAtual.isPresent()) {
                return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
            }

            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            PessoaJuridica pessoaJuridica = pessoaAtual.get();

            if (dto.getInscricaoEstadual() != null) {
                pessoaJuridica.setInscricaoEstadual(dto.getInscricaoEstadual());
            }
            if (dto.getNomeFantasia() != null) {
                pessoaJuridica.setNomeFantasia(dto.getNomeFantasia());
            }

            if (dto.getNome() != null) {
                if (dto.getNome().length() < 3) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_nome"));
                }
                pessoaJuridica.setNome(dto.getNome());
            }

            if (dto.getCnpj() != null) {
                CNPJ cnpj = new CNPJ(dto.getCnpj());
                if (!cnpj.isCNPJ()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_cnpj_invalido"));
                }
                if (!dto.getCnpj().equals(pessoaJuridica.getCnpj()) && pessoaJuridicaRepository.cnpjExistente(dto.getCnpj())) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_cnpj_ja_cadastrado"));
                }
                pessoaJuridica.setCnpj(dto.getCnpj());
            }

            Endereco endereco = pessoaJuridica.getEndereco();

            if (dto.getCidade() != null) {
                Optional<Cidade> cidade = cidadeRepository.findById(dto.getCidade());
                if (!cidade.isPresent()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_cidade"));
                }
                endereco.setCidade(cidade.get());
            }

            if (dto.getBairro() != null) {
                if (dto.getBairro().isEmpty()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_bairro"));
                }
                endereco.setBairro(dto.getBairro());
            }

            if (dto.getCep() != null) {
                if (dto.getCep().length() != 8) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_cep"));
                }
                endereco.setCep(dto.getCep());
            }

            if (dto.getLogradouro() != null) {
                if (dto.getLogradouro().isEmpty()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_logradouro"));
                }
                endereco.setLogradouro(dto.getLogradouro());
            }

            List<Telefone> telefones = null;
            if (dto.getTelefones() != null) {
                telefones = new ArrayList<>();
                if (dto.getTelefones().isEmpty()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_telefone"));
                }
                for (String tel : dto.getTelefones()) {
                    if (!tel.isEmpty()) {
                        br.com.aicare.di.api_rest.uteis.NumeroTelefone validadorTelefone = new NumeroTelefone(tel);
                        if (!validadorTelefone.isValido()) {
                            return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_telefone_invalido") + ": " + tel);
                        }
                        Telefone telefone = new Telefone();
                        telefone.setNumero(tel);
                        telefones.add(telefone);
                    }
                }
            }

            List<Email> emails = null;
            if (dto.getEmails() != null) {
                emails = new ArrayList<>();
                for (String email : dto.getEmails()) {
                    if (!email.isEmpty()) {
                        if (!new br.com.aicare.di.api_rest.uteis.Email(email).isValido()) {
                            return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_email_invalido") + ": " + email);
                        }
                        Email e = new Email();
                        e.setEmail(email);
                        emails.add(e);
                    }
                }
            }

            if (telefones != null) {
                telefoneRepository.deleteInBatch(pessoaJuridica.getTelefones());
                pessoaJuridica.setTelefones(new HashSet<>(telefones));
            }
            if (pessoaJuridica.getTelefones().size() < 1) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_juridica_telefone"));
            }

            if (emails != null) {
                emailRepository.deleteInBatch(pessoaJuridica.getEmails());
                pessoaJuridica.setEmails(new HashSet<>(emails));
            }

            endereco.setLatitude(null);
            endereco.setLongitude(null);

            PessoaJuridica atualizado = pessoaJuridicaRepository.save(pessoaJuridica);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (atualizado == null) {
                return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
            }

            //Se foi criado com sucesso, retorno o objeto atualizado
            return new ResponseEntity<>(atualizado, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar uma pessoa juridica", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }

}
