package org.example.gestionuser.entities;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ProfileValidationStatusConverter implements AttributeConverter<ProfileValidationStatus, String> {

    @Override
    public String convertToDatabaseColumn(ProfileValidationStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public ProfileValidationStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        String trimmed = dbData.trim();
        if (trimmed.isEmpty()) return ProfileValidationStatus.NOT_REQUIRED;
        try {
            return ProfileValidationStatus.valueOf(trimmed);
        } catch (IllegalArgumentException ex) {
            // unknown value stored in DB: fallback to a safe default
            return ProfileValidationStatus.NOT_REQUIRED;
        }
    }
}

