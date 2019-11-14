/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.equipamento.bomba_infusao;

public class BombaDTO {

    private String serialNumber;
    private Integer modelo;

    private Boolean operativo;
    private Integer tipoBomba;
    private String versaoSoftware;
    private String versaoHardware;

    public BombaDTO() {

    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getModelo() {
        return modelo;
    }

    public void setModelo(Integer modelo) {
        this.modelo = modelo;
    }

    public Boolean isOperativo() {
        return operativo;
    }

    public void setOperativo(Boolean operativo) {
        this.operativo = operativo;
    }

    public Integer getTipoBomba() {
        return tipoBomba;
    }

    public void setTipoBomba(Integer tipoBomba) {
        this.tipoBomba = tipoBomba;
    }

    public String getVersaoSoftware() {
        return versaoSoftware;
    }

    public void setVersaoSoftware(String versaoSoftware) {
        this.versaoSoftware = versaoSoftware;
    }

    public String getVersaoHardware() {
        return versaoHardware;
    }

    public void setVersaoHardware(String versaoHardware) {
        this.versaoHardware = versaoHardware;
    }

}
