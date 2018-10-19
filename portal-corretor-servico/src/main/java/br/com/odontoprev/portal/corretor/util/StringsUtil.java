package br.com.odontoprev.portal.corretor.util;

import java.text.Normalizer;

// h t t p s : / / s t a c k o v e r f l o w . c o m /questions/15190656/easy-way-to-remove-accents-from-a-unicode-string
//201808271707 - esert - COR-617
public class StringsUtil {

	public static String stripAccents(String s) 
	{
	    s = Normalizer.normalize(s, Normalizer.Form.NFD);
	    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    return s;
	}
}
