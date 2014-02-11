/**
 * @author giric
 */
package main;

import java.util.Random;

import Util.Colors;
import Util.Utilities;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PGraphicsJava2D;
import processing.core.PVector;
import processing.opengl.PGraphics3D;
import wordcram.Placers;
import wordcram.Word;
import wordcram.WordColorer;
import wordcram.WordCram;
import wordcram.WordPlacer;

@SuppressWarnings("unused")
public class WordCloud extends BasicControl{
	WordCram wordCram;
	PGraphicsJava2D canvas;
	PApplet parent;
	
	public WordCloud(PApplet parent, float x, float y, float width, float height, String fileName) {
		super(parent, x, y, width, height);
		
		this.parent = parent;
		canvas = new PGraphicsJava2D();
		canvas.setSize((int)Utilities.width, (int)Utilities.height);
		
		wordCram = new WordCram(parent)
			//.fromTextString(string)
			.fromTextFile(parent.sketchPath + "/data/"+fileName)
			//.withFont(parent.createFont("../../"+Utilities.font, 1))
	      
			// scale according to the scale factor.--------------------------------------
			// change the values here ---------------------------------------------------
			.sizedByWeight((int)Utilities.Converter(2), (int)Utilities.Converter(10))             
			.maxAttemptsToPlaceWord(50)
	        .angledAt(0)
	        //.withCustomCanvas(canvas)
	        .minShapeSize((int)Utilities.Converter(1))
	        //.withPlacer(customPlacer())
	        .withPlacer(horizBandAnchoredLeftCustom())
	        .maxNumberOfWordsToDraw((int)Utilities.Converter(100))
	        //.withPlacer(Placers.centerClump())
	        // .withPlacer(Placers.horizLine())
	        //.withPlacer(Placers.horizBandAnchoredLeft())
	        //.withPlacer(Placers.wave())
            // For this one, try setting the sketch size to 1000x1000.
            //.withPlacer(Placers.swirl())
            //.sizedByWeight(8, 30)

            //.withPlacer(Placers.upperLeft())
            //.sizedByWeight(10, 40)
            .withColorer(darkerByWeight())
          ;

	}
	
	WordPlacer horizBandAnchoredLeftCustom() {
        final Random r = new Random();
        return new WordPlacer() {
            public PVector place(Word word, int wordIndex, int wordsCount,
                    int wordImageWidth, int wordImageHeight, int fieldWidth,
                    int fieldHeight) {
                float x = parent.map((1 - word.weight) * fieldWidth * r.nextFloat(), 0, Utilities.width, myX + Utilities.Converter(10), myX + myWidth); // big=left, small=right
                float y = parent.map(((float) fieldHeight) * 0.5f, 0, Utilities.height, myY + Utilities.Converter(5), myY + myHeight);
                return new PVector(x, y);
            }
        };
    }
	
	WordPlacer customPlacer() {
	        final Random r = new Random();
	        final float stdev = 0.4f;

	        return new WordPlacer() {

	            public PVector place(Word word, int wordIndex, int wordsCount,
	                    int wordImageWidth, int wordImageHeight, int fieldWidth, int fieldHeight) {
	            		int x = getOneUnder(myX + myWidth - wordImageWidth, myX);
	            		int y = getOneUnder(myY + myHeight - wordImageHeight, myY);
	            		/*while(x  < myX || x > myX + myWidth)
	            			x = getOneUnder(fieldWidth - wordImageWidth);
	            		while(y < myY || y > myY + myHeight)
	            			y = getOneUnder(fieldHeight - wordImageHeight);
	            		y = y - (int)myHeight/2;
	            		x = x + (int)myWidth/2;
	            		
	            		parent.fill(255);
	            		parent.strokeWeight(5);
	            		parent.stroke(255);
	            		parent.point(x, y);
	            		*/
	                return new PVector(x,y);
	            }

	            private int getOneUnder(float upperLimit, float lowerLimit) {
	                return PApplet.round(PApplet.map((float) r.nextGaussian()
	                        * stdev, -2, 2, lowerLimit, upperLimit));
	            }
	        };
	}

	WordColorer darkerByWeight() {
		  return new WordColorer() {
		    public  int colorFor(Word word) {
		      int r = (int)parent.random(255);
		      int g = (int)parent.random(255);
		      int b = (int)parent.random(255);
		      return parent.color(r, g, b);
		    }
		  }; // Don't forget the semi-colon for the return statement.
		}

	
	
	@Override
	public void draw() {
		parent.rectMode(PConstants.CORNER);
		parent.noFill();
		parent.stroke(Colors.DARK_WHITE);
		parent.strokeWeight(Utilities.Converter(0.5));
		parent.rect(myX, myY, myWidth, myHeight);
		
		wordCram.drawAll();
		/*
		if (wordCram.hasMore()) {
		    wordCram.drawNext();
		  }
		*/
		Word[] words  = wordCram.getWords();  
		  Word[] skippedWords = wordCram.getSkippedWords();
		  //println(skippedWords);  // Probably a long list!
		  // println("Placed " + (words.length - skippedWords.length) + 
		  //    " words out of " + words.length);
		  for (int i=0; i<words.length; i++) {
		    Word word = words[i];

		    // This will show either where the word was placed, or why it was skipped.
		    //println(word);

		    if (word.wasPlaced()) {
		      // println(word.word + " was placed!");
		    }
		    if (word.wasSkipped()) {
		      //System.out.println(word.word + " was skipped!");

		      if (word.wasSkippedBecause() == WordCram.NO_SPACE) {
		        //System.out.println(word.word + " was skipped because there was no room");
		      }
		    }
		  }
		
	}
	
	public void clearArea() {
		parent.fill(Colors.DARK_GRAY);
		parent.beginShape();
			parent.vertex(Utilities.width *3/6, 0);
			parent.vertex(Utilities.width *3/6, Utilities.height / 3);
			parent.vertex(Utilities.width *5/6, Utilities.height / 3);
			parent.vertex(Utilities.width *5/6, 0);
		parent.endShape(PConstants.CLOSE);
	}
}
