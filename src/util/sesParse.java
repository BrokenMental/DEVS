package util;

import java.util.Iterator;
import java.util.regex.Pattern;

import GenCol.Queue;

public class sesParse {
    public sesParse() {
    }
    
    public String reduceToRoot(String s) {
        if (s.endsWith("es")) {
            return s.substring(0, s.length() - 2);
        }
        if (s.endsWith("s")) {
            return s.substring(0, s.length() - 1);
        }
        if (s.endsWith("ed")) {
            return s.substring(0, s.length() - 2);
        }
        if (s.endsWith("ing")) {
            return s.substring(0, s.length() - 3);
        }
        
        return s;
    }
    
    public String[] removeNulls(String[] groups) {
        Queue q = new Queue();
        for (int i = 0; i < groups.length; i++) {
            if (!groups[i].equals("")) {
                q.add(groups[i]);
            }
        }
        
        String[] res = new String[q.size()];
        Iterator it = q.iterator();
        int j = 0;
        while (it.hasNext()) {
            res[j] = (String) it.next();
            j++;
        }
        return res;
    }
    
    public String[] getParts(String sentence) {
        Pattern p = Pattern.compile(" +");
        String[] groups = p.split(sentence);
        for (int i = 0; i < groups.length; i++) {
            groups[i] = groups[i].trim();
        }
        groups = removeNulls(groups);
        return groups;
    }
    
    EnsembleSetUtil det = stringOps.toEnsembleSet(new String[] { "a", "the",
    "an", "this", "that", "these", "those", "A", "The", "An", "This",
    "That", "These", "Those" });
    
    public String[] removeDeterminers(String[] groups) {
        return removeConnectors(groups, det);
    }
    
    EnsembleSetUtil prep = stringOps.toEnsembleSet(new String[] { "from",
    "From" });
    
    public String[] removePreps(String[] groups) {
        return removeConnectors(groups, prep);
    }
    
    public String[] removeConnectors(String[] groups, EnsembleSetUtil det) {
        Queue grps = stringOps.toQueue(groups);
        grps.removeAll(det);
        String[] res = new String[grps.size()];
        int j = 0;
        for (int i = 0; i < groups.length; i++) {
            if (!groups[i].equals("") && grps.contains(groups[i])) {
                res[j] = groups[i];
                j++;
                // if (j>=res.length)break;
            }
        }
        return res;
    }
    
    public String[] replaceHis(String[] groups) {
        EnsembleSetUtil possPro = stringOps.toEnsembleSet(new String[] { "his",
        "her", "his/her", "their", "its" });
        int indexOfHis = -1;
        for (int i = 0; i < groups.length; i++) {
            if (possPro.contains(groups[i])) {
                indexOfHis = i;
                break;
            }
        }
        if (indexOfHis == -1) {
            return groups;
        }
        groups[indexOfHis] = groups[0] + "'s";
        return groups;
    }
    
    public static String[] contractAllPossessive(String[] groups) {
        
        String[] grps = groups;
        String[] res = contractPossessive(grps);
        while (res.length < grps.length) {
            grps = res;
            res = contractPossessive(grps);
        }
        
        return res;
        
    }
    
    public static String[] contractPossessive(String[] groups) {
        int indexOfAppostrophe = -1;
        for (int i = 0; i < groups.length; i++) {
            if (groups[i].indexOf("'") > 0) {
                indexOfAppostrophe = i;
                break;
            }
        }
        if (indexOfAppostrophe == -1) {
            return groups;
        }
        
        String[] res = new String[groups.length - 1];
        for (int i = 0; i < groups.length; i++) {
            if (i < indexOfAppostrophe) {
                res[i] = groups[i];
            } else if (i == indexOfAppostrophe) {
                res[i] = groups[i].substring(0, groups[i].length() - 2) + "."
                        + groups[i + 1];
            } else if (i < groups.length - 1) {
                res[i] = groups[i + 1];
            }
        }
        return res;
    }
    
    public static String[] contractComesFrom(String[] groups) {
        
        String[] res = new String[groups.length];
        for (int i = 0; i < groups.length; i++) {
            if (groups[i].equals("comes")) {
                res[i] = "=";
            } else {
                res[i] = groups[i];
            }
        }
        return res;
    }
    
    public FunctionUtil parse(String sentance) {
        String[] groups = contractAllPossessive(replaceHis(removeDeterminers(getParts(sentance))));
        if (checkCoupling(groups) != null) {
            return checkCoupling(groups);
        } else if (checkRange(groups) != null) {
            return checkRange(groups);
        } else if (checkComputedVar(groups) != null) {
            return checkComputedVar(groups);
        } else if (checkHasAttrsdNConnective(groups) != null) {
            return checkHasAttrsdNConnective(groups);
        } else if (checkSpecializedIsLike(groups) != null) {
            return checkSpecializedIsLike(groups);
        } else if (checkSpecializedNConnective(groups) != null) {
            return checkSpecializedNConnective(groups);
        } else if (checkDecomposedIsLike(groups) != null) {
            return checkDecomposedIsLike(groups);
        } else if (checkDecomposedNConnective(groups) != null) {
            return checkDecomposedNConnective(groups);
        } else if (checkDecomposedNMultiple(groups) != null) {
            return checkDecomposedNMultiple(groups);
        } else {
            return null;
        }
    }
    
    public String[] subsequence(String[] groups, int begin, int end) {
        String s[] = new String[end - begin + 1];
        for (int i = begin; i <= end; i++) {
            s[i - begin] = groups[i];
        }
        return s;
    }
    
    public String recompose(String[] groups, int begin, int end) {
        String s = "";
        for (int i = begin; i <= end; i++) {
            s += groups[i] + " ";
        }
        return s;
    }
    
    public FunctionUtil checkDecomposedIsLike(String[] groups) {
        FunctionUtil f = new FunctionUtil();
        if (groups.length >= 7 && groups[0].toLowerCase().equals("from")
        && groups[2].startsWith("perspective")
        && groups[groups.length - 2].equals("like")) {
            f.put("entity", groups[groups.length - 4]);
            f.put("isLike", groups[groups.length - 1]);
            f.put("aspect", groups[1] + "Dec");
            return f;
        }
        return null;
    }
    
    public PairUtil parseConnective(String s) {
        Queue q = new Queue();
        String connective = "";
        Pattern p = Pattern.compile(",");
        String[] groups = p.split(s);
        if (groups.length == 1) { // no commas, look for single entity or
            // binary clause
            p = Pattern.compile(" ");
            groups = p.split(s);
            if (groups.length == 1) {
                connective = "and";
                q.add(groups[0]);
            }
            
            else if (groups[1].equals("and")) {
                connective = "and";
                q.add(groups[0]);
                q.add(groups[2]);
            } else if (groups[1].equals("or")) {
                connective = "or";
                q.add(groups[0]);
                q.add(groups[2]);
            }
        } else { // look for comma separated items
            q.add(groups[0]); // connective does not occur first
            for (int i = 1; i < groups.length; i++) {
                groups[i] = groups[i].trim();
                if (groups[i].startsWith("and")) {
                    if (connective.equals("")) {
                        connective = "and";
                    }
                    if (connective.equals("and")) {
                        q.add(groups[i].substring(4).trim());
                    }
                } else if (groups[i].startsWith("or")) {
                    if (connective.equals("")) {
                        connective = "or";
                    }
                    if (connective.equals("or")) {
                        q.add(groups[i].substring(3).trim());
                    }
                } else {
                    q.add(groups[i]);
                }
            }
        }
        return new PairUtil(connective, q);
    }
    
    public FunctionUtil checkPerspective(String[] groups) {
        FunctionUtil f = new FunctionUtil();
        if (groups.length >= 3 && groups[0].toLowerCase().equals("from")
        && groups[2].startsWith("perspective")) {
            f.put("aspect", groups[1] + "Dec");
            return f;
        }
        return null;
    }
    
    public FunctionUtil checkDecomposedNConnective(String[] groups) {
        FunctionUtil f = checkPerspective(groups);
        if (f == null) {
            return f;
        }
        
        
        String ss = recompose(groups,0,groups.length-1);
        int ind = ss.indexOf(",");
        if (ind>-1)
            ss = ss.substring(0,ind)+", "+ss.substring(ind+1);
        groups = getParts(ss);
        
        String[] grps = subsequence(groups, 3, groups.length - 1);
        if (checkDecomposed(grps) != null) {
            f.putAll(checkDecomposed(grps));
        }
        String s = recompose(groups, 7, groups.length - 1);
        PairUtil pp = parseConnective(s);
        String connective = (String) pp.getKey();
        if (connective.equals("and")) {
            f.put("entities", pp.getValue());
            
            return f;
        }
        return null;
    }
    
    public FunctionUtil checkDecomposed(String[] groups) {
        FunctionUtil f = new FunctionUtil();
        if (groups.length >= 4 && groups[2].toLowerCase().equals("made")) {
            f.put("entity", groups[0]);
            
            return f;
        }
        return null;
    }
    
    public FunctionUtil checkDecomposedNMultiple(String[] groups) {
        FunctionUtil f = checkPerspective(groups);
        if (f == null) {
            return f;
        }
        String ss = recompose(groups,0,groups.length-1);
        int ind = ss.indexOf(",");
        if (ind>-1)
            ss = ss.substring(0,ind)+", "+ss.substring(ind+1);
        groups = getParts(ss);
       
        
        String[] grps = subsequence(groups, 3, groups.length - 1);
        if (checkDecomposed(grps) != null) {
            f.putAll(checkDecomposed(grps));
        }
        if (groups[7].equals("more") && groups.length >= 10) {
            String multiasp = (String) f.get("aspect");
            f.put("multiAspect", multiasp.substring(0, multiasp.length() - 3)
            + "MultiAsp");
            f.remove("aspect");
            f.put("entities", groups[10]);
            return f;
        }
        return null;
    }
    
    public FunctionUtil checkSpecializedIsLike(String[] groups) {
        FunctionUtil f = new FunctionUtil();
        if (groups.length >= 6 && groups[2].equals("like")) {
            f.put("entity", groups[0]);
            f.put("isLike", groups[3]);
            f.put("specialization", groups[5] + "Spec");
            return f;
        }
        return null;
    }
    
    public FunctionUtil checkSpecialized(String[] groups) {
        FunctionUtil f = new FunctionUtil();
        if (groups.length >= 3 && groups[1].equals("can")) {
            f.put("entity", groups[0]);
            return f;
        }
        return null;
    }
    
    public FunctionUtil checkIn(String[] groups) {
        FunctionUtil f = new FunctionUtil();
        if (groups.length >= 2 && groups[0].toLowerCase().equals("in")) {
            f.put("specialization", groups[1] + "Spec");
            return f;
        }
        return null;
    }
    
    public FunctionUtil checkSpecializedNConnective(String[] groups) {
        FunctionUtil f = checkSpecialized(groups);
        if (f == null) {
            return f;
        }
        String[] grps = subsequence(groups, groups.length - 2,
                groups.length - 1);
        if (checkIn(grps) != null) {
            f.putAll(checkIn(grps));
            
            String s = recompose(groups, 3, groups.length - 3);
            PairUtil pp = parseConnective(s);
            String connective = (String) pp.getKey();
            if (connective.equals("or")) {
                f.put("entities", pp.getValue());
                return f;
            }
        }
        return null;
    }
    
    public FunctionUtil checkHas(String[] groups) {
        FunctionUtil f = new FunctionUtil();
        if (groups.length >= 3 && groups[1].equals("has")) {
            f.put("entity", groups[0]);
            return f;
        }
        return null;
    }
    
    public FunctionUtil checkHasAttrsdNConnective(String[] groups) {
        FunctionUtil f = checkHas(groups);
        if (f == null) {
            return f;
        }
        String[] grps = subsequence(groups, 2, groups.length - 1);
        
        String s = recompose(grps, 0, grps.length - 1);
        PairUtil pp = parseConnective(s);
        String connective = (String) pp.getKey();
        if (connective.equals("and")) {
            f.put("vars", pp.getValue());
            return f;
        }
        return null;
    }
    
    public FunctionUtil checkComeFrom(String[] groups) {
        FunctionUtil f = new FunctionUtil();
        EnsembleSetUtil es = stringOps.toEnsembleSet(groups);
        if (es.contains("comes") && es.contains("where")) {
            return f;
        }
        return null;
    }
    
    public FunctionUtil checkComputedVar(String[] groups) {
        FunctionUtil f = checkComeFrom(groups);
        if (f == null) {
            return f;
        }
        String sentence = recompose(groups, 0, groups.length - 1);
        Pattern p = Pattern.compile("where");
        String[] grps = p.split(sentence);
        String ss = "";
        for (int i = 0; i < grps.length; i++) {
            String[] is = getParts(grps[i]);
            is = contractComesFrom(removePreps(is));
            if (i == 0) {
                f.put("entity", is[0].substring(0, is[0].indexOf(".")));
            }
            ss += recompose(is, 0, is.length - 1) + ";";
        }
        f.put("function", ss);
        return f;
    }
    
    public FunctionUtil checkRange(String[] groups) {
        FunctionUtil f = new FunctionUtil();
        if (groups.length >= 5 && groups[0].equals("range")) {
            // f.put("var", groups[2].substring(groups[2].indexOf(".") + 1,
            // groups[2].length()));
            f.put("entity.var", groups[2]);
            f.put("rangeSpec", groups[4]);
            if (groups.length >= 8 && groups[5].equals("with")) {
                String[] grps = subsequence(groups, 7, groups.length - 1);
                String s = recompose(grps, 0, grps.length - 1);
                int index = s.indexOf("[");
                if (index == -1)
                    index = s.indexOf("(");
                if (index > -1) {
                    f.put("values", s.trim() + "interval");
                } else {
                    PairUtil pp = parseConnective(s);
                    String connective = (String) pp.getKey();
                    if (connective.equals("and")) {
                        f.put("values", pp.getValue().toString());
                    }
                }
            }
            return f;
        } else {
            return null;
        }
    }
    
    public FunctionUtil checkCoupling(String[] groups) {
        FunctionUtil f = checkPerspective(groups);
        if (f == null) {
            return f;
        }
        String s = recompose(groups,0,groups.length-1);
        int ind = s.indexOf(",");
        if (ind>-1)
            s = s.substring(0,ind)+", "+s.substring(ind+1);
        groups = getParts(s);
        String[] grps = subsequence(groups, 3, groups.length - 1);
        if (grps.length < 5)
            return null;
        if (!grps[1].equals("sends") && !grps[3].equals("to"))
            return null;
        f.put("source", grps[0]);
        f.put("outport", grps[2]);
        f.put("destination", grps[4]);
        if (grps.length == 7) {
            if (grps[5].equals("as"))
                f.put("inport", grps[6]);
        }
        return f;
    }
    
    public static void main(String[] args) {
        sesParse p = new sesParse();
        String sentence = "From /* the Composite perspective, "
                + "host sends  notifyOut to IABM ";
        int ind = sentence.indexOf("/*");
        System.out.println(ind);
        String[] groups = p.removeDeterminers(p.getParts(sentence));
        FunctionUtil f = p.checkCoupling(groups);
        if (f != null)
            f.print();
        
        System.exit(3);
        
    }
}
