package com.zup.propostas.validacoes;

import com.zup.propostas.exceptions.PropostaNotValidException;
import com.zup.propostas.utils.Criptografia;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
public class UniquePropostaValidator implements ConstraintValidator<UniqueProposta, String> {

    private String atributos;
    private Class<?> dClass;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(UniqueProposta parametros) {
        atributos = parametros.fieldName();
        dClass = parametros.domainClass();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String valueHash = Criptografia.getHashSHA512(value);
        Query query = entityManager.createQuery("select c from "+dClass.getName()+" c where c."+atributos+"=:value");
        query.setParameter("value", valueHash);
        List<?> list = query.getResultList();
        Assert.state(list.size() <= 1, "Foi encontrado mais de um "+dClass+" com o atributo "+atributos+" = "+value);
        if (!list.isEmpty()){
            throw new PropostaNotValidException(context.getDefaultConstraintMessageTemplate());
        }

        return list.isEmpty();


    }
}