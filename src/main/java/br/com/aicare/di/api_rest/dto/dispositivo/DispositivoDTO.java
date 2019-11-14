/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.dispositivo;

/**
 *
 * @author Paulo Collares
 */
public class DispositivoDTO {

    private Integer id;
    private String mac;
    private String tipo;
    private Integer modelo;
    private Integer idTipo;
    private String nameSpace;
    private Boolean operativo;
    private String versaoFirmware;
    private String versaoHardware;

    public DispositivoDTO() {

    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getModelo() {
        return modelo;
    }

    public void setModelo(Integer modelo) {
        this.modelo = modelo;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public Boolean isOperativo() {
        return operativo;
    }

    public void setOperativo(Boolean operativo) {
        this.operativo = operativo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getVersaoFirmware() {
        return versaoFirmware;
    }

    public void setVersaoFirmware(String versaoFirmware) {
        this.versaoFirmware = versaoFirmware;
    }

    public String getVersaoHardware() {
        return versaoHardware;
    }

    public void setVersaoHardware(String versaoHardware) {
        this.versaoHardware = versaoHardware;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
