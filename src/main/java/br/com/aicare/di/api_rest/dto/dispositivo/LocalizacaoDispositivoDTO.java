/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.dispositivo;

/**
 *
 * @author Paulo Collares
 */
public class LocalizacaoDispositivoDTO {

    private double latitude;
    private double longitude;
    private boolean geoOk;

    private String endereco;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isGeoOk() {
        return geoOk;
    }

    public void setGeoOk(boolean geoOk) {
        this.geoOk = geoOk;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

}
