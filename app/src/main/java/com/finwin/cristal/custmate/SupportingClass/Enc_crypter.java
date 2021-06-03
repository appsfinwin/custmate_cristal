package com.finwin.cristal.custmate.SupportingClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Enc_crypter {

    HashMap<Character, String> mappingsEncr = new HashMap<Character, String>();
    HashMap<String, Character> mappingsDecr = new HashMap<String, Character>();

    public Enc_crypter() {
        //-----------------------------//
        mappingsEncr.put('a', "101");
        mappingsEncr.put('b', "102");
        mappingsEncr.put('c', "103");
        mappingsEncr.put('d', "104");
        mappingsEncr.put('e', "105");
        mappingsEncr.put('f', "106");
        mappingsEncr.put('g', "107");
        mappingsEncr.put('h', "108");
        mappingsEncr.put('i', "109");
        mappingsEncr.put('j', "110");
        mappingsEncr.put('k', "111");
        mappingsEncr.put('l', "112");
        mappingsEncr.put('m', "113");
        mappingsEncr.put('n', "114");
        mappingsEncr.put('o', "115");
        mappingsEncr.put('p', "116");
        mappingsEncr.put('q', "117");
        mappingsEncr.put('r', "118");
        mappingsEncr.put('s', "119");
        mappingsEncr.put('t', "120");
        mappingsEncr.put('u', "121");
        mappingsEncr.put('v', "122");
        mappingsEncr.put('w', "123");
        mappingsEncr.put('x', "124");
        mappingsEncr.put('y', "125");
        mappingsEncr.put('z', "126");
        mappingsEncr.put('A', "201");
        mappingsEncr.put('B', "202");
        mappingsEncr.put('C', "203");
        mappingsEncr.put('D', "204");
        mappingsEncr.put('E', "205");
        mappingsEncr.put('F', "206");
        mappingsEncr.put('G', "207");
        mappingsEncr.put('H', "208");
        mappingsEncr.put('I', "209");
        mappingsEncr.put('J', "210");
        mappingsEncr.put('K', "211");
        mappingsEncr.put('L', "212");
        mappingsEncr.put('M', "213");
        mappingsEncr.put('N', "214");
        mappingsEncr.put('O', "215");
        mappingsEncr.put('P', "216");
        mappingsEncr.put('Q', "217");
        mappingsEncr.put('R', "218");
        mappingsEncr.put('S', "219");
        mappingsEncr.put('T', "220");
        mappingsEncr.put('U', "221");
        mappingsEncr.put('V', "222");
        mappingsEncr.put('W', "223");
        mappingsEncr.put('X', "224");
        mappingsEncr.put('Y', "225");
        mappingsEncr.put('Z', "226");
        mappingsEncr.put('0', "301");
        mappingsEncr.put('1', "302");
        mappingsEncr.put('2', "303");
        mappingsEncr.put('3', "304");
        mappingsEncr.put('4', "305");
        mappingsEncr.put('5', "306");
        mappingsEncr.put('6', "307");
        mappingsEncr.put('7', "308");
        mappingsEncr.put('8', "309");
        mappingsEncr.put('9', "310");
        mappingsEncr.put(':', "400");
        mappingsEncr.put(';', "401");
        mappingsEncr.put('/', "402");
        mappingsEncr.put('@', "403");
        mappingsEncr.put('#', "404");
        mappingsEncr.put('$', "405");
        mappingsEncr.put('%', "406");
        mappingsEncr.put('&', "407");
        mappingsEncr.put('*', "408");
        mappingsEncr.put('(', "409");
        mappingsEncr.put(')', "410");
        mappingsEncr.put('<', "411");
        mappingsEncr.put('>', "412");
        mappingsEncr.put('?', "413");
        mappingsEncr.put('.', "414");
        mappingsEncr.put('_', "415");
        mappingsEncr.put(' ', "416");
        mappingsEncr.put('-', "417");
        mappingsEncr.put('{', "418");
        mappingsEncr.put('}', "419");
        mappingsEncr.put('[', "420");
        mappingsEncr.put(']', "421");
        mappingsEncr.put(',', "422");
        mappingsEncr.put('~', "423");
        mappingsEncr.put('!', "424");
        mappingsEncr.put('"', "425");
        mappingsEncr.put('+', "426");
        mappingsEncr.put('|', "427");

        //-----------------------------//
        mappingsDecr.put("101", 'a');
        mappingsDecr.put("102", 'b');
        mappingsDecr.put("103", 'c');
        mappingsDecr.put("104", 'd');
        mappingsDecr.put("105", 'e');
        mappingsDecr.put("106", 'f');
        mappingsDecr.put("107", 'g');
        mappingsDecr.put("108", 'h');
        mappingsDecr.put("109", 'i');
        mappingsDecr.put("110", 'j');
        mappingsDecr.put("111", 'k');
        mappingsDecr.put("112", 'l');
        mappingsDecr.put("113", 'm');
        mappingsDecr.put("114", 'n');
        mappingsDecr.put("115", 'o');
        mappingsDecr.put("116", 'p');
        mappingsDecr.put("117", 'q');
        mappingsDecr.put("118", 'r');
        mappingsDecr.put("119", 's');
        mappingsDecr.put("120", 't');
        mappingsDecr.put("121", 'u');
        mappingsDecr.put("122", 'v');
        mappingsDecr.put("123", 'w');
        mappingsDecr.put("124", 'x');
        mappingsDecr.put("125", 'y');
        mappingsDecr.put("126", 'z');
        mappingsDecr.put("201", 'A');
        mappingsDecr.put("202", 'B');
        mappingsDecr.put("203", 'C');
        mappingsDecr.put("204", 'D');
        mappingsDecr.put("205", 'E');
        mappingsDecr.put("206", 'F');
        mappingsDecr.put("207", 'G');
        mappingsDecr.put("208", 'H');
        mappingsDecr.put("209", 'I');
        mappingsDecr.put("210", 'J');
        mappingsDecr.put("211", 'K');
        mappingsDecr.put("212", 'L');
        mappingsDecr.put("213", 'M');
        mappingsDecr.put("214", 'N');
        mappingsDecr.put("215", 'O');
        mappingsDecr.put("216", 'P');
        mappingsDecr.put("217", 'Q');
        mappingsDecr.put("218", 'R');
        mappingsDecr.put("219", 'S');
        mappingsDecr.put("220", 'T');
        mappingsDecr.put("221", 'U');
        mappingsDecr.put("222", 'V');
        mappingsDecr.put("223", 'W');
        mappingsDecr.put("224", 'X');
        mappingsDecr.put("225", 'Y');
        mappingsDecr.put("226", 'Z');
        mappingsDecr.put("301", '0');
        mappingsDecr.put("302", '1');
        mappingsDecr.put("303", '2');
        mappingsDecr.put("304", '3');
        mappingsDecr.put("305", '4');
        mappingsDecr.put("306", '5');
        mappingsDecr.put("307", '6');
        mappingsDecr.put("308", '7');
        mappingsDecr.put("309", '8');
        mappingsDecr.put("310", '9');
        mappingsDecr.put("400", ':');
        mappingsDecr.put("401", ';');
        mappingsDecr.put("402", '/');
        mappingsDecr.put("403", '@');
        mappingsDecr.put("404", '#');
        mappingsDecr.put("405", '$');
        mappingsDecr.put("406", '%');
        mappingsDecr.put("407", '&');
        mappingsDecr.put("408", '*');
        mappingsDecr.put("409", '(');
        mappingsDecr.put("410", ')');
        mappingsDecr.put("411", '<');
        mappingsDecr.put("412", '>');
        mappingsDecr.put("413", '?');
        mappingsDecr.put("414", '.');
        mappingsDecr.put("415", '_');
        mappingsDecr.put("416", ' ');
        mappingsDecr.put("417", '-');
        mappingsDecr.put("418", '{');
        mappingsDecr.put("419", '}');
        mappingsDecr.put("420", '[');
        mappingsDecr.put("421", ']');
        mappingsDecr.put("422", ',');
        mappingsDecr.put("423", '~');
        mappingsDecr.put("424", '!');
        mappingsDecr.put("425", '"');
        mappingsDecr.put("426", '+');
        mappingsDecr.put("427", '|');

        //-----------------------------//
    }

    public String ConvertString(String _input) {
        String output = "";
        try {
            char[] charArray = _input.toCharArray();
            for (Character item : charArray) {
                output += mappingsEncr.get(item);
            }
        } catch (Exception e) {

        }
        return output;
    }

    public String decrypter(String _input) {
        String output = "";
        try {
            String[] result = splitString(_input);

            for (String item : result) {
                output += mappingsDecr.get(item);
            }
        } catch (Exception e) {
        }
        return output;
    }

    public String[] splitString(String _input) {
        List<String> termsList = new ArrayList<>();
        try {
            if (_input != null) {
                int splitlen = 3;
                int i = 0;
                while (i < _input.length()) {
                    termsList.add(_input.substring(i, Math.min(i + splitlen, _input.length())));
                    i += splitlen;
                }
            }
        } catch (Exception e) {
        }
        return termsList.toArray(new String[termsList.size()]);
    }

    public String revDecString(String _input) {
        String str = _input;
        String reverse = "";
        try {
            for (int i = str.length() - 1; i >= 0; i--) {
                reverse = reverse + str.charAt(i);
            }
        } catch (Exception e) {
        }
        return decrypter(reverse);
    }

    public String conRevString(String _input) {
        String str = ConvertString(_input);
        String reverse = "";
        try {
            for (int i = str.length() - 1; i >= 0; i--) {
                reverse = reverse + str.charAt(i);
            }
        } catch (Exception e) {
//            ErrorLog.submitError(getClass(), this.getClass().getSimpleName() + ":" + new Object() {
//            }.getClass().getEnclosingMethod().getName(), e.toString());
        }
        return reverse;
    }

}
