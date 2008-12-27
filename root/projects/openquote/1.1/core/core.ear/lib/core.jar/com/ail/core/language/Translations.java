/* Copyright Applied Industrial Logic Limited 2006. All rights Reserved */
/*
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later 
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51 
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.ail.core.language;

import java.util.ArrayList;
import java.util.Collection;

import com.ail.core.Type;

/**
 * This class acts as a grouping for sets of related translations (@link Translation).
 * Helper methods are also supplied to assist in the act of translation (@link {@link #translate(String)} and {@link #translate(String, String)})
 * Translatons define a default language
 */
public class Translations extends Type {
    private String defaultLanguage=null;
    
    private Collection<Translation> translation=null;
    
    public Translations() {
        translation=new ArrayList<Translation>();
    }
    
    public Translations(String defaultLanguage) {
        this();
        setDefaultLanguage(defaultLanguage);
    }

    /**
     * @return the translation
     */
    public Collection<Translation> getTranslation() {
        return translation;
    }

    /**
     * @param translation the translation to set
     */
    public void setTranslation(Collection<Translation> translation) {
        this.translation = translation;
    }


    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public String translate(String key) {
        return translate(com.ail.core.Locale.getThreadLocale().getISO3Language(), key);
    }
    
    public String translate(String language, String key) {
        boolean languageFound=false;
        String result=null;
        String nextLanguage=null;
        
        for(Translation t: translation) {
            if (t.getLanguage().equals(language)) {
                languageFound=true;
                result=t.translate(key);
                if (result!=null) {
                    return result;
                }
                else {
                    nextLanguage=t.getExtendsLanguage();
                }
            }
        }
        
        if (!languageFound) {
            return translate(defaultLanguage, key);
        }
        
        if (nextLanguage!=null) {
            return translate(nextLanguage, key);
        }
        else {
            return "$$"+key+"$$";
        }
    }
}
