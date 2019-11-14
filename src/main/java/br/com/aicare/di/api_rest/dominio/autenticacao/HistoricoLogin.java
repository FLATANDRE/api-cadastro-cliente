/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.autenticacao;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author FERNANDA
 */
@Entity
@Table(name = "historico_login")
public class HistoricoLogin implements Serializable {

    @Id
    @SequenceGenerator(name = "historico_login_seq", sequenceName = "historico_login_seq", allocationSize = 1)
    @GeneratedValue(generator = "historico_login_seq", strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "data_hora")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataHora;

    @Column(name = "navegador")
    private String navegador;

    @Column(name = "ip")
    private String ip;

    @Column(name = "metodo")
    private String metodo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public HistoricoLogin() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ApiModelProperty(notes = "Data do historico", dataType = "Data", example = "2019-05-01", required = true)
    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    @Transient
    public String getDataHoraFormatada() {
        if (getDataHora() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return sdf.format(getDataHora());
        }
        return null;
    }

    @ApiModelProperty(notes = "Navegador do historico", required = true)
    public String getNavegador() {
        return navegador;
    }

    public void setNavegador(String navegador) {
        this.navegador = navegador;
    }

    @ApiModelProperty(notes = "IP do historico", required = true)
    public String getIP() {
        return ip;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public static HistoricoLogin fabricar(HttpServletRequest request) {
        HistoricoLogin historicoLogin = new HistoricoLogin();

        //IP
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        historicoLogin.setIP(ip);

        historicoLogin.setNavegador(request.getHeader("User-Agent"));

        historicoLogin.setDataHora(new Date());

        return historicoLogin;
    }
}
