/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apriori;

import java.io.*;
import java.util.*;

/**
 *
 * @author SAUMYA
 */
public class Apriori {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        AprioriCalculation ap = new AprioriCalculation();
        ap.aprioriProcess();
    }
    
}

/****************************************************************
 * ClassName : AprioriCalculation
 * Purpose : Generate Apriori Itemset
 *****************************************************************/


class AprioriCalculation{
    
    Vector<String> candidates = new Vector<String>();
    String configFile = "G:\\CollegeStuffs\\Project\\AprioriAlgo\\config.txt";
    String transFile = "G:\\CollegeStuffs\\Project\\AprioriAlgo\\transa.txt";
    String outputFile = "G:\\CollegeStuffs\\Project\\AprioriAlgo\\AprioriOutput.txt";
    
    int numItems;
    int numTransactions;
    double minsup;
    String oneVal[];
    String itemSep = " ";
    
    
    /*******************************************************
     * MethodName : aprioriProcess
     * Purpose : Generate the apriori itemset
     *******************************************************/
    
    public void aprioriProcess(){
    
        Date d;
        long start, end;
        int itemsetNumber = 0;
        
        getConfig();
        
        System.out.println( " Apriori Algorithm Has Started!! " );
        
        d = new Date();
        start = d.getTime();
        
        do{
            itemsetNumber++;
            
            generateCandidates( itemsetNumber );
            calculateFrequentItemSets( itemsetNumber );
            
            if( candidates.size() != 0 ) {
                
                System.out.println( " frequent " + itemsetNumber + "-itemsets " );
                System.out.println( candidates );
            }
        } while( candidates.size() > 1 );  
        
        d = new Date();
        end = d.getTime();
        
        System.out.println( " Execution Time = " + ((double)( end - start)/1000) + " seconds " );
    
    }
    
    /*******************************************************************************
     * 
     * MethodName : getInput
     * Purpose : get user input from System.in
     *******************************************************************************/
    
    
    public static String getInput( ) {
        
        String input = "";
        BufferedReader reader = new BufferedReader( new InputStreamReader( System.in) );
        
        try{
            input = reader.readLine();
        } catch( Exception e ) {
            System.out.println( e );
        }
        
        return input;
    }
    
    /***********************************************************************************
     * 
     *Method name : getConfig
     * Purpose : get the configuration information( config filename,vtransaction filename )
     *          configFile and transFile will change appropriately
     * 
     ***********************************************************************************/
    
    
    private void getConfig( ) {
        
        FileWriter fw;
        BufferedWriter file_out;
        
        String input = "";
        
        System.out.println( " Default configuration: ");
        System.out.println( "\tRegular transaction file with " + itemSep + " item Separator ");
        System.out.println( " Config File : " +  configFile );
        System.out.println( "Transa File " + transFile );
        System.out.println( " Output File " + outputFile );
        System.out.println( "\nPress 'C' to change item Separator, configuration file and transaction file ");
        System.out.print( " or any other key to continue ");
        
        input = getInput();
        
        if( input.compareToIgnoreCase("c") == 0 ) {
            
            System.out.println( " Enter new transaction file name( return for '"+transFile+"' ): ");
            input = getInput();
            if( input.compareToIgnoreCase("") != 0 )
                transFile = input;
            
            System.out.println( " Enter new configuration file name( return for '"+configFile+"' ): ");
            input = getInput();
            if( input.compareToIgnoreCase("") != 0 )
                configFile = input;
            
            System.out.println( " Enter new output file name( return for '"+outputFile+"' ): ");
            input = getInput();
            if( input.compareToIgnoreCase("") != 0 )
                outputFile = input;
            
            System.out.println( " Filenames Changed ");
            
            System.out.println( " Enter the separating character(s) for items( return for '"+itemSep+"' ): ");
            input = getInput();
            if( input.compareToIgnoreCase("") != 0 )
                itemSep = input;         
            
        } try {
            
            FileInputStream file_in = new FileInputStream( configFile);
            BufferedReader data_in = new BufferedReader( new InputStreamReader( file_in ) );
            
            numItems = Integer.valueOf( data_in.readLine()).intValue();
            
            minsup = Double.valueOf( data_in.readLine()).doubleValue();
            
            System.out.print( "\t Input Configuration: " + numItems + " items " + numTransactions + " transactions," );
            System.out.println( " minsup = " + minsup + "%");
            System.out.println();
            
            minsup /= 100.0;
            
            oneVal = new String[numItems];
            
            System.out.println("Enter y to change the value of each row recognises as a '1' : ");
             if(getInput().compareToIgnoreCase("y")==0) {
                for( int i = 0; i< oneVal.length; i++ ) {
                    System.out.println( " Enter value for column #" + ( i + 1 )+ ":");
                    oneVal[i] = getInput();
                }
            } else {
                for( int i = 0; i < oneVal.length; i++ )
                    oneVal[i] = "1";
                
                fw = new FileWriter( outputFile );
                file_out = new BufferedWriter( fw );
                file_out.write(numTransactions + "\n");
                file_out.write(numItems + "\n*****\n");
                file_out.close();
            }
        } catch( IOException e ) {
            System.out.println( e );
        }
    }
    
    /********************************************************************************
     * 
     * MethodName : generateCandidates
     * Purpose: Generate all candidates for nth item set,
     *          these candidates are stored in the candidates class vector.
     * 
     *******************************************************************************/
    
    
    public void generateCandidates( int n ){
        
        Vector<String> tempCandidates = new Vector<String>();
        
        String str1, str2;
        StringTokenizer st1, st2;
        
        if( n == 1 ) {
            for( int i = 1; i <= numItems; i++ ) {
                tempCandidates.add( Integer.toString(i));
            }
        } else if ( n == 2) {
            for( int i = 0; i < candidates.size(); i++ ) {
                st1 = new StringTokenizer( candidates.get(i));
                str1 = st1.nextToken();
                
                
                for( int j = i + 1; j < candidates.size(); j++ ) {
                    st2 = new StringTokenizer( candidates.elementAt(j));
                    str2 = st2.nextToken();
                    tempCandidates.add( str1 + " " + str2 );
                }
            }
        } else {
            for( int i = 0; i < candidates.size(); i++ ) {
                for( int j = i+1; j < candidates.size(); j++ ) {
                    
                    str1 = new String();
                    str2 = new String();
                    
                    st1 = new StringTokenizer( candidates.get(i));
                    st2 = new StringTokenizer( candidates.get(j));
                    
                    for( int s = 0; s < n-2; s++ ) {
                        str1  = str1 + " " + st1.nextToken();
                        str2 = str2 + " " + st2.nextToken();
                    }
                    
                    if( str2.compareToIgnoreCase( str1 ) == 0 )
                        tempCandidates.add( ( str1 + " " + st1.nextToken() + st2.nextToken()).trim());
                                        
                }
            }
        }
        
        candidates.clear();
        candidates = new Vector<String>( tempCandidates );
        tempCandidates.clear();
    
    }
    
    /*******************************************************************************
     * 
     * MethodName : calculateFrequentItemSets
     * Purpose : Determine which items are frequent in frequent nth itemSet 
     *              from all possible candidates
     * 
     ******************************************************************************/
    
    private void calculateFrequentItemSets( int n ) {
        
        Vector<String> frequentCandidates = new Vector<String>();
        FileInputStream file_in;
        BufferedReader data_in;
        
        FileWriter fw;
        BufferedWriter file_out;
        
        StringTokenizer st, stFile;
        boolean match;
        boolean trans[] = new boolean[numItems];
        int count[] = new int[candidates.size()];
        
        try{
            
            fw = new FileWriter( outputFile, true );
            file_out = new BufferedWriter( fw );
            
            file_in = new FileInputStream( transFile );
            data_in = new BufferedReader( new InputStreamReader(file_in) );
            
            
            for( int i = 0; i < numTransactions; i++ ) {
                
                stFile = new StringTokenizer( data_in.readLine(), itemSep);
                
                for( int j = 0; j < numItems; j++ )
                    trans[j] = ( stFile.nextToken().compareToIgnoreCase(oneVal[j] ) == 0 );
                
                for( int c = 0; c < candidates.size(); c++ ) {
                    match = false;
                    st = new StringTokenizer( candidates.get(c) );
                    
                    while( st.hasMoreTokens()) {
                        match = ( trans[Integer.valueOf(st.nextToken()) - 1 ]);
                        if( !match ) break;
                        
                    }
                    if( match )
                        count[c]++;
                }
            }
            
            for( int i = 0; i < candidates.size(); i++ ) {
                
                if( (count[i]/ ( double)numTransactions) >= minsup ) {
                    frequentCandidates.add(candidates.get(i));
                    file_out.write(candidates.get(i) + "," + count[i]/(double)numTransactions + "\n");
                }
            }
            file_out.write("-\n");
            file_out.close();
        } catch( IOException e ) {
            System.out.println( e );
        }
        
        candidates.clear();
        candidates = new Vector<String>( frequentCandidates);
        frequentCandidates.clear();
        
    }
    
}