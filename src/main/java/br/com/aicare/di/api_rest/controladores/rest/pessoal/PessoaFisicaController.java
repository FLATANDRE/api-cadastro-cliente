/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.pessoal;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.localizacao.Cidade;
import br.com.aicare.di.api_rest.dominio.localizacao.Email;
import br.com.aicare.di.api_rest.dominio.localizacao.Endereco;
import br.com.aicare.di.api_rest.dominio.localizacao.Telefone;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaFisica;
import br.com.aicare.di.api_rest.dto.pessoal.PessoaFisicaDTO;
import br.com.aicare.di.api_rest.dto.pessoal.PessoaFisicaSimpleResponseDTO;
import br.com.aicare.di.api_rest.repository.localizacao.CidadeRepository;
import br.com.aicare.di.api_rest.repository.localizacao.EmailRepository;
import br.com.aicare.di.api_rest.repository.localizacao.TelefoneRepository;
import br.com.aicare.di.api_rest.repository.pessoal.PessoaFisicaRepository;
import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.uteis.CPF;
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
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Paulo Collares
 */
@RestController
@CrossOrigin
@Secured({"ROLE_ADA", "ROLE_ADM", "ROLE_ADH"})
@RequestMapping("/pessoal/pessoas-fisicas")
@Api(tags = "Pessoa Física", description = "Pessoas Físicas")
public class PessoaFisicaController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    TelefoneRepository telefoneRepository;

    @Autowired
    EmailRepository emailRepository;

    @ApiOperation(value = "Lista as pessoas fisicas ou Busca pelo CPF, Nome, ou RG")
    @GetMapping()
    public Page<PessoaFisica> listar(
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

        Page<PessoaFisica> resultPage;

        if (q == null) {
            resultPage = pessoaFisicaRepository.findAll(pageable);
        } else {
            resultPage = pessoaFisicaRepository.busca(q.toLowerCase(), pageable);
        }

        return resultPage;
    }

    @ApiOperation(value = "Lista as pessoas fisicas com informações reduzidas")
    @GetMapping("/all")
    public Collection<PessoaFisicaSimpleResponseDTO> listar() {
        Collection<PessoaFisicaSimpleResponseDTO> all = pessoaFisicaRepository.all();
        return all;
    }

    @ApiOperation(value = "Busca uma pessoa fisica pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PessoaFisica> buscar(@PathVariable Integer id) {
        Optional<PessoaFisica> pessoa = pessoaFisicaRepository.findById(id);

        if (!pessoa.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(pessoa.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca uma pessoa fisica pelo CPF")
    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<PessoaFisica> buscarCPF(@PathVariable String cpf) {
        PessoaFisica pessoa = pessoaFisicaRepository.findByCpf(cpf);

        if (pessoa == null) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(pessoa, HttpStatus.OK);
    }

    @ApiOperation(value = "Cria uma nova pessoa fisica")
    @PostMapping()
    public ResponseEntity<PessoaFisica> criar(@RequestBody PessoaFisicaDTO dto, UriComponentsBuilder ucBuilder) {
        try {
            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            PessoaFisica pessoaFisica = new PessoaFisica();

            if (dto.getNome() == null || dto.getNome().length() < 3) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_nome"));
            }
            if (!dto.getNome().matches("([a-zA-Z]+) ([a-zA-Z]+)(\\D*)")) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_sobrenome"));
            }
            pessoaFisica.setNome(dto.getNome());

            pessoaFisica.setRg(dto.getRg());

            if (dto.getDataNascimento() == null || dto.getDataNascimento().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_data_nascimento"));
            }
            try {
                if (!dto.getDataNascimento().matches("\\d{4}-\\d{2}-\\d{2}")) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_data_nascimento"));
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                Date date = sdf.parse(dto.getDataNascimento());
                if (date.toInstant().isAfter(Instant.now())) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_data_nascimento"));
                }
                pessoaFisica.setDataNascimento(date);
            } catch (Exception e) {
                LOGGER.error("Erro ao realizar o convesão da data de nascimento dto", e);
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_data_nascimento"));
            }

            CPF cpf = new CPF(dto.getCpf());
            if (!cpf.isValido()) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_cpf_invalido"));
            }
            if (pessoaFisicaRepository.cpfExistente(dto.getCpf())) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_cpf_ja_cadastrado"));
            }
            pessoaFisica.setCpf(dto.getCpf());

            List<Email> emails = new ArrayList<>();
            if (dto.getEmails() != null && dto.getEmails().size() > 0) {
                for (String email : dto.getEmails()) {
                    if (!email.isEmpty()) {
                        if (!new br.com.aicare.di.api_rest.uteis.Email(email).isValido()) {
                            return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_email_invalido") + ": " + email);
                        }
                        Email e = new Email();
                        e.setEmail(email);
                        emails.add(e);
                    }
                }
            }

            List<Telefone> telefones = new ArrayList<>();
            if (dto.getTelefones() == null || dto.getTelefones().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_telefone"));
            }
            for (String tel : dto.getTelefones()) {
                if (!tel.isEmpty()) {
                    NumeroTelefone validadorTelefone = new NumeroTelefone(tel);
                    if (!validadorTelefone.isValido()) {
                        return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_telefone_invalido") + ": " + tel);
                    }
                    Telefone telefone = new Telefone();
                    telefone.setNumero(tel);
                    telefones.add(telefone);
                }
            }
            if (telefones.size() < 1) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_telefone"));
            }

            if (dto.getCidade() == null) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_cidade"));
            }
            Optional<Cidade> cidade = cidadeRepository.findById(dto.getCidade());
            if (!cidade.isPresent()) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_cidade_invalida"));
            }
            Endereco endereco = new Endereco();
            endereco.setCidade(cidade.get());

            if (dto.getBairro() == null || dto.getBairro().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_bairro"));
            }
            endereco.setBairro(dto.getBairro());

            if (dto.getCep() == null || dto.getCep().length() != 8) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_cep"));
            }
            endereco.setCep(dto.getCep());

            if (dto.getLogradouro() == null || dto.getLogradouro().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_logradouro"));
            }
            endereco.setLogradouro(dto.getLogradouro());

            //Salvo o objeto utilizando o repositório
            pessoaFisica.setTelefones(new HashSet<>(telefones));
            pessoaFisica.setEmails(new HashSet<>(emails));
            pessoaFisica.setEndereco(endereco);
            PessoaFisica novo = pessoaFisicaRepository.save(pessoaFisica);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null) {
                return ApiError.badRequest("Ocorreu algum erro na criação desse objeto");
            }

            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao criar uma pessoa fisica", e);
            return ApiError.internalServerError(Translator.toLocale("erro_criacao"));
        }
    }

    @ApiOperation(value = "Atualiza uma pessoa fisica")
    @PutMapping(value = "/{id}")
    public ResponseEntity<PessoaFisica> atualizar(@PathVariable("id") int id, @RequestBody PessoaFisicaDTO dto) {
        try {
            Optional<PessoaFisica> pessoaAtual = pessoaFisicaRepository.findById(id);

            if (!pessoaAtual.isPresent()) {
                return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
            }

            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            PessoaFisica pessoaFisica = pessoaAtual.get();

            if (dto.getNome() != null) {
                if (dto.getNome().length() < 3) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_nome"));
                }
                if (!dto.getNome().matches("([a-zA-Z]+) ([a-zA-Z]+)(\\D*)")) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_sobrenome"));
                }
                pessoaFisica.setNome(dto.getNome());
            }

            if (dto.getDataNascimento() != null) {
                if (dto.getDataNascimento().isEmpty()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_data_nascimento"));
                }
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false);
                    Date date = sdf.parse(dto.getDataNascimento());
                    if (date.toInstant().isAfter(Instant.now())) {
                        return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_data_nascimento"));
                    }
                    pessoaFisica.setDataNascimento(date);
                } catch (Exception e) {
                    LOGGER.error("Erro ao realizar o convesão da data de nascimento dto", e);
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_data_nascimento"));
                }
            }

            if (dto.getRg() != null) {
                pessoaFisica.setRg(dto.getRg());
            }

            if (dto.getCpf() != null) {
                CPF cpf = new CPF(dto.getCpf());
                if (!cpf.isValido()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_cpf_invalido"));
                }
                if (!dto.getCpf().equals(pessoaFisica.getCpf()) && pessoaFisicaRepository.cpfExistente(dto.getCpf())) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_cpf_ja_cadastrado"));
                }
                pessoaFisica.setCpf(dto.getCpf());
            }

            Endereco endereco = pessoaFisica.getEndereco();

            if (dto.getCidade() != null) {
                Optional<Cidade> cidade = cidadeRepository.findById(dto.getCidade());
                if (!cidade.isPresent()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_cidade_invalida"));
                }
                endereco.setCidade(cidade.get());
            }

            if (dto.getBairro() != null) {
                if (dto.getBairro().isEmpty()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_bairro"));
                }
                endereco.setBairro(dto.getBairro());
            }

            if (dto.getCep() != null) {
                if (dto.getCep().length() != 8) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_cep"));
                }
                endereco.setCep(dto.getCep());
            }

            if (dto.getLogradouro() != null) {
                if (dto.getLogradouro().isEmpty()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_logradouro"));
                }
                endereco.setLogradouro(dto.getLogradouro());
            }

            List<Telefone> telefones = null;
            if (dto.getTelefones() != null) {
                telefones = new ArrayList<>();
                if (dto.getTelefones().isEmpty()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_telefone"));
                }
                for (String tel : dto.getTelefones()) {
                    if (!tel.isEmpty()) {
                        NumeroTelefone validadorTelefone = new NumeroTelefone(tel);
                        if (!validadorTelefone.isValido()) {
                            return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_telefone_invalido") + ": " + tel);
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
                            return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_email_invalido") + ": " + email);
                        }
                        Email e = new Email();
                        e.setEmail(email);
                        emails.add(e);
                    }
                }
            }

            if (telefones != null) {
                if (telefones.size() < 1) {
                    return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_telefone"));
                }
                telefoneRepository.deleteInBatch(pessoaFisica.getTelefones());
                pessoaFisica.setTelefones(new HashSet<>(telefones));
            }
            if (pessoaFisica.getTelefones().size() < 1) {
                return ApiError.badRequest(Translator.toLocale("validacao_pessoa_fisica_telefone"));
            }

            if (emails != null) {
                emailRepository.deleteInBatch(pessoaFisica.getEmails());
                pessoaFisica.setEmails(new HashSet<>(emails));
            }

            PessoaFisica atualizado = pessoaFisicaRepository.save(pessoaFisica);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (atualizado == null) {
                return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
            }

            //Se foi criado com sucesso, retorno o objeto atualizado
            return new ResponseEntity<>(atualizado, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar uma pessoa fisica", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }

}
