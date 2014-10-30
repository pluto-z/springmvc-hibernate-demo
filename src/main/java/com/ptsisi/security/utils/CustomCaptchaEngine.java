package com.ptsisi.security.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.FileDictionary;
import com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

public class CustomCaptchaEngine extends ListImageCaptchaEngine {

	@Override
	protected void buildInitialFactories() {
		int minWordLength = 4;
		int maxWordLength = 5;
		int fontSize = 20;
		int imageWidth = 90;
		int imageHeight = 35;
		// word generator
		WordGenerator dictionnaryWords = new ComposeDictionaryWordGenerator(
				new FileDictionary("toddlist"));
		// word2image components
		TextPaster randomPaster = new DecoratedRandomTextPaster(minWordLength,
				maxWordLength, new RandomListColorGenerator(new Color[] {
				new Color(67, 54, 85), new Color(228, 13, 89),
				new Color(120, 44, 68), new Color(223, 170, 27),
				new Color(23, 70, 127), new Color(123, 170, 227),
				new Color(23, 170, 27), new Color(220, 34, 11),
				new Color(23, 67, 172) }), new TextDecorator[] { });
		BackgroundGenerator background = new UniColorBackgroundGenerator(
				imageWidth, imageHeight, new Color(238, 238, 238));
		FontGenerator font = new RandomFontGenerator(fontSize, fontSize,
				new Font[] { new Font("nyala", Font.BOLD, fontSize),
						new Font("Bell MT", Font.PLAIN, fontSize),
						new Font("Credit valley", Font.BOLD, fontSize) });
		ImageDeformation postDef = new ImageDeformationByFilters(
				new ImageFilter[] { });
		ImageDeformation backDef = new ImageDeformationByFilters(
				new ImageFilter[] { });
		ImageDeformation textDef = new ImageDeformationByFilters(
				new ImageFilter[] { });
		WordToImage word2image = new DeformedComposedWordToImage(font,
				background, randomPaster, backDef, textDef, postDef);
		addFactory(new GimpyFactory(dictionnaryWords, word2image));
	}

}
