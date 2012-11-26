
package com.acims.fddevs;

import java.util.Iterator;
import java.util.regex.Pattern;

import GenCol.*;
import util.*;


public class FDdevsParse {
    public FDdevsParse() {
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
    "From", "In" ,"in", "for","For", "to","To", "then","Then"});
    
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
    
    public String[] replaceCommas(String[] groups) {
        String s = recompose(groups,0,groups.length-1);
        s= replaceCommas(s);
        return getParts(s);
    }
    
    public String replaceCommas(String s) {
        boolean found = true;
        while(found){
            int ind = s.indexOf(",");
            if (ind>-1)
                s = s.substring(0,ind)+" "+s.substring(ind+1);
            else found = false;
        }
        return s;
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
    
    public FunctionUtil parseSpecialCheckHoldIn(String sentance) {
        String[] groups =
                removePreps(contractAllPossessive(replaceHis(
                removeDeterminers(getParts(replaceCommas(sentance))))));
        if (checkStart(groups) == null){
            if (checkSpecialHoldIn(groups) != null) {
                return checkSpecialHoldIn(groups);
            }
        }
        return null;
    }
    public FunctionUtil parse(String sentance) {
        String[] groups =
                removePreps(contractAllPossessive(
                replaceHis(removeDeterminers(getParts(replaceCommas(sentance))))));
        if (checkStart(groups) != null) {
            return checkStart(groups);
        } else if (checkHoldIn(groups) != null) {
            return checkHoldIn(groups);
        }else
            if (checkPassivateIn(groups) != null) {
            return checkPassivateIn(groups);
            } else
                if (checkGo(groups) != null) {
            return checkGo(groups);
                } else
                    if (checkOut(groups) != null) {
            return checkOut(groups);
                    } else
                        if (checkWhenReceive(groups) != null) {
            return checkWhenReceive(groups);
                        } else
                            return null;
    }
    
    
    public FunctionUtil checkStart(String[] groups) {
        FunctionUtil f = new FunctionUtil();
        if (groups.length <3) return null;
        if (groups[0].equals("start")){
            f.put("initialize","true");
            if (checkHoldIn(subsequence(groups,1,groups.length-1)) != null)
                f.putAll(checkHoldIn(subsequence(groups,1,groups.length-1)));
            else   if (checkPassivateIn(subsequence(groups,1,groups.length-1)) != null)
                f.putAll(checkPassivateIn(subsequence(groups,1,groups.length-1)));
            return f;
        }
        return null;
    }
    
    public FunctionUtil checkHoldIn(String[] groups) {
        FunctionUtil f = new FunctionUtil();
        if (groups.length <3) return null;
        if (!groups[0].equals("hold")
        && !groups[2].equals("time")
        )
            return null;
        f.put("startingState", groups[1]);
        f.put("timeOut", groups[3]);
        if (groups.length > 4 && groups[4].equals("output")){
            f.put("output",groups[5]);
            f.put("specialOutput","present");
        }
        if (groups.length > 6  && groups[6].equals("and")&& groups[7].equals("go")){
            f.put("targetState",groups[8]);
            f.put("specialtransition","present");
        } else if (groups.length > 4 && groups[4].equals("go")){
            f.put("targetState",groups[5]);
            f.put("specialTransition","present");
        }
        return f;
    }
    
    public FunctionUtil checkSpecialHoldIn(String[] groups) {
        FunctionUtil f = new FunctionUtil();
        if (groups.length <3) return null;
        if (!groups[0].equals("hold")
        && !groups[2].equals("time")
        )
            return null;
        if (groups.length > 4 && groups[4].equals("output")){
            
            f.put("startingState", groups[1]);
            f.put("dummyState", groups[1]);
            f.put("timeOut", groups[3]);
            f.put("output",groups[5]);
            f.put("specialOutput","present");
        }
        if (groups.length > 6  && groups[6].equals("and")&& groups[7].equals("go")){
            if (f.get("startingSate")== null) f.put("startingState", groups[1]);
            f.put("timeOut", groups[3]);
            f.put("targetState",groups[8]);
            f.put("specialTransition","present");
        } else if (groups.length > 4 && groups[4].equals("go")){
            //if (f.get("startingSate")== null) f.put("startingState", groups[1]);
            if (f.get("dummyState")== null) f.put("dummyState", groups[1]);
            f.put("timeOut", groups[3]);
            f.put("targetState",groups[5]);
            f.put("specialTransition","present");
        }
        return f;
    }
    
    public FunctionUtil checkPassivateIn(String[] groups) {
        FunctionUtil f = new FunctionUtil();
        if (groups.length <1) return null;
        if (!groups[0].equals("passivate")
        )
            return null;
        f.put("startingState", groups[1]);
        return f;
    }
    public FunctionUtil checkGo(String[] groups) {
        FunctionUtil f = new FunctionUtil();
        if (groups.length <3) return null;
        if (!groups[1].equals("go")
        )
            return null;
        f.put("startingState", groups[0]);
        f.put("targetState", groups[2]);
        return f;
    }
    
    public FunctionUtil checkOut(String[] groups) {
        FunctionUtil f = new FunctionUtil();
        if (groups.length <4) return null;
        if (!groups[0].equals("after")
        && !groups[0].equals("After")
        && !groups[2].equals("output")
        )
            return null;
        f.put("startingState", groups[1]);
        f.put("output", groups[3]);
        return f;
    }
    
    public FunctionUtil checkWhenReceive(String[] groups) {
        EnsembleSetUtil phase =
                stringOps.toEnsembleSet(new String[] { "phase", "Phase" });
        groups = removeConnectors(groups, phase);
        FunctionUtil f = new FunctionUtil();
        if (groups.length <7) return null;
        if (!groups[0].equals("when")
        && !groups[2].equals("and")
        && !groups[3].equals("receive")
        && !groups[5].equals("go")
        )
            return null;
        f.put("startingState", groups[1]);
        f.put("input", groups[4]);
        f.put("targetState", groups[6]);
        if (groups.length == 8 && groups[7].equals("eventually")){
            f.put("reschedule", "false");
        }
        return f;
    }
    
    
    public static void main(String[] args) {
        FDdevsParse p = new FDdevsParse();
        // String sentence = "hold in busy for time 10 then output job and go to processing";
        //   String sentence = "hold in busy for time 10 then go to processing";
        //  String sentence = "hold in busy for time 10" ;
        //  String sentence = "from busy go to passive ";
        //String sentence = "After busy then output job ";
        // \
        String sentence = "When in phase passive and receive X  then go to outPhase";
        FunctionUtil f = p.parse(sentence);
        //	System.out.println(f);
        
        if (f != null)
            f.print();
        
        System.exit(3);
        
    }
}

