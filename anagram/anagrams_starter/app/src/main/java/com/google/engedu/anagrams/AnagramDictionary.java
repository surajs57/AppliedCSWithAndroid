/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    public class AnagramDictionary {

// Constant Variables
        private static final intMIN_NUM_ANAGRAMS = 5;
        private static final intDEFAULT_WORD_LENGTH = 3;
        private static final intMAX_WORD_LENGTH = 7;

// Incremental Word Length Variable
        private static intwordLength = DEFAULT_WORD_LENGTH;

        // Random Class to generate Random Numbers
        private Random random = new Random();

        // Data Structures to Hold Information
        HashSet<String>wordSet = new HashSet<>();
        ArrayList<String>wordList = new ArrayList<>();
        HashMap<String, ArrayList<String>>lettersToWord = new HashMap<>();
        HashMap<Integer, ArrayList<String>>sizeToWords = new HashMap<>();

        public AnagramDictionary(InputStreamwordListStream) throws IOException {
            BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
            String line;
            ArrayList<String>wordMapList;

            while((line = in.readLine()) != null) {
                String word = line.trim();
                wordSet.add(word);
                wordList.add(word);

// Populate the sizeToWordsHashMap
                if (sizeToWords.containsKey(word.length())) {
                    wordMapList = sizeToWords.get(word.length());
                    wordMapList.add(word);
                    sizeToWords.put(word.length(), wordMapList);
                } else {
                    ArrayList<String>newWordList = new ArrayList<>();
                    newWordList.add(word);
                    sizeToWords.put(word.length(), newWordList);
                }

                ArrayList<String>sortedList = new ArrayList<>();
                String sortedWord = sortLetters(word);

// Populate the lettersToWordHashMap
                if ( !(lettersToWord.containsKey(sortedWord)) ) {
                    sortedList.add(word);
                    lettersToWord.put(sortedWord, sortedList);
                } else {
                    sortedList = lettersToWord.get(sortedWord);
                    sortedList.add(word);
                    lettersToWord.put(sortedWord, sortedList);
                }
            }
        }

        // This function checks if the word is valid.
        // the provided word is a valid dictionary word (i.e., in wordSet), and
        // the word does not contain the base word as a substring.
        public booleanisGoodWord(String word, String base) {
            if (wordSet.contains(word) && !(base.contains(word))) {
                return true;
            } else {
                return false;
            }

        }

        // Default Anagram Fetcher
        public ArrayList<String>getAnagrams(String targetWord) {
            ArrayList<String>resultList = new ArrayList<>();
            return resultList;
        }

        // Anagram Fetcher for One More Extra Letter
        public ArrayList<String>getAnagramsWithOneMoreLetter(String word) {
            ArrayList<String>tempList;
            ArrayList<String>resultList = new ArrayList<>();

            for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
                String anagram = word + alphabet;
                String sortedAnagram = sortLetters(anagram);

                if (lettersToWord.containsKey(sortedAnagram)) {
                    tempList = lettersToWord.get(sortedAnagram);

                    for (inti = 0; i<tempList.size(); i++) {
                        if ( !(tempList.get(i).contains(word)) ) {
                            resultList.add(tempList.get(i));
                        }
                    }
                }
            }

            return resultList;
        }

        // Anagram Fetcher for Two More Extra Letters
        public ArrayList<String>getAnagramsWithTwoMoreLetters(String word) {
            ArrayList<String>tempList;
            ArrayList<String>resultList = new ArrayList<>();

            for (char firstAlphabet = 'a'; firstAlphabet<= 'z'; firstAlphabet++) {
                for (char secondAlphabet = 'a'; secondAlphabet<= 'z'; secondAlphabet++) {
                    String anagram = word + firstAlphabet + secondAlphabet;
                    String sortedAnagram = sortLetters(anagram);

                    if (lettersToWord.containsKey(sortedAnagram)) {
                        tempList = lettersToWord.get(sortedAnagram);

                        for (inti = 0; i<tempList.size(); i++) {
                            if (!(tempList.get(i).contains(word))) {
                                resultList.add(tempList.get(i));
                            }
                        }
                    }
                }
            }

            return resultList;
        }

        // Function to pick a suitable and valid starter word,
        // and increment for every subsequent level
        public String pickGoodStarterWord() {
            intrandomNumber;
            String starterWord;

            do {
                randomNumber = random.nextInt(sizeToWords.get(wordLength).size());
                starterWord = sizeToWords.get(wordLength).get(randomNumber);
            } while (getAnagramsWithTwoMoreLetters(starterWord).size() < MIN_NUM_ANAGRAMS);

            if (wordLength< MAX_WORD_LENGTH) {
                wordLength++;
            }

            return starterWord;
        }

        // Function to sort a single string alphabetically,
        // and returns a new string
        public String sortLetters(String word) {
            char[] characters = word.toCharArray();
            Arrays.sort(characters);
            String sortedWord = new String(characters);
            return sortedWord;
        }
    }
