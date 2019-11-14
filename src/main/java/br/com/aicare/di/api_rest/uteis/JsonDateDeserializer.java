package br.com.aicare.di.api_rest.uteis;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;

public class JsonDateDeserializer extends JsonDeserializer<Object> {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Object deserialize(final JsonParser value, final DeserializationContext ctxt)
	    throws IOException, JsonProcessingException {
	format.setLenient(false);
	if (value != null) {
	    final String valueStr = value.getValueAsString();
	    if (valueStr.length() > 0 && value.getCurrentToken().equals(JsonToken.VALUE_STRING)) {
		try {
		    return  format.parse(valueStr);
		} catch (ParseException e) {
		    LOGGER.warn(Translator.toLocale("data_invalida"), e);
		    throw new IOException(Translator.toLocale("data_invalida"));
		}
	    }
	}
	return null;
    }
    
    
}
