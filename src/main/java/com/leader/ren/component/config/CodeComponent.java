package com.leader.ren.component.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "kaptcha", name = "enable", havingValue = "true")
public class CodeComponent {
	/**
	 * 是否有边框
	 */
	@Value("${kaptcha.border.enabled}")
	String borderEnabled;

	/**
	 * 边框颜色
	 */
	@Value("${kaptcha.border.color}")
	String borderColor;

	/**
	 * 边框粗细度
	 */
	@Value("${kaptcha.border.thickness}")
	String borderThickness;

	/**
	 * 验证码生成器
	 */
	@Value("${kaptcha.producer.impl}")
	String producerImpl;

	/**
	 * 验证码文本生成器
	 */
	@Value("${kaptcha.textproducer.impl}")
	String textproducerImpl;

	/**
	 * 验证码文本字符内容范围
	 */
	@Value("${kaptcha.textproducer.char.string}")
	String textproducerCharString;

	/**
	 * 验证码文本字符长度
	 */
	@Value("${kaptcha.textproducer.char.length}")
	String textproducerCharLength;

	/**
	 * 验证码文本字体样式  默认为Arial,Courier
	 */
	@Value("${kaptcha.textproducer.font.names}")
	String textproducerFontNames;

	/**
	 * 验证码文本字符大小
	 */
	@Value("${kaptcha.textproducer.font.size}")
	String textproducerFontSize;

	/**
	 * 验证码文本字符颜色
	 */
	@Value("${kaptcha.textproducer.font.color}")
	String textproducerFontColor;

	/**
	 * 验证码文本字符间距
	 */
	@Value("${kaptcha.textproducer.char.space}")
	String textproducerCharSpace;

	/**
	 * 验证码噪点生成对象  NoNoise:没有
	 */
	@Value("${kaptcha.noise.impl}")
	String noiseImpl;

	/**
	 * 验证码噪点颜色
	 */
	@Value("${kaptcha.noise.color}")
	String noiseColor;

	/**
	 * 渲染效果：水纹：WaterRipple；鱼眼：FishEyeGimpy；阴影：ShadowGimpy
	 */
	@Value("${kaptcha.obscurificator.impl}")
	String obscurificatorImpl;

	/**
	 * 验证码文本字符渲染
	 */
	@Value("${kaptcha.wordrenderer.impl}")
	String wordRendererImpl;

	/**
	 * 验证码背景生成器
	 */
	@Value("${kaptcha.background.impl}")
	String backgroundImpl;

	/**
	 * 验证码背景颜色渐进起始
	 */
	@Value("${kaptcha.background.clear.from}")
	String backgroundClearFrom;

	/**
	 * 验证码背景颜色渐进结束
	 */
	@Value("${kaptcha.background.clear.to}")
	String backgroundClearTo;

	/**
	 * 验证码图片宽度
	 */
	@Value("${kaptcha.image.width}")
	String imageWidth;

	/**
	 * 验证码图片高度
	 */
	@Value("${kaptcha.image.height}")
	String imageHeight;

	@Bean(name = "codeProperties")
	public Properties codeProperties() {
		Properties prop = new Properties();

		//是否有边框
		if("true".equals(borderEnabled) || "yes".equals(borderEnabled)){
			borderEnabled = "yes";
		} else {
			borderEnabled = "no";
		}
		prop.setProperty(Constants.KAPTCHA_BORDER, borderEnabled);
		//边框颜色
		prop.setProperty(Constants.KAPTCHA_BORDER_COLOR, borderColor);
		//边框粗细度
		prop.setProperty(Constants.KAPTCHA_BORDER_THICKNESS, borderThickness);
		//验证码生成器
		prop.setProperty(Constants.KAPTCHA_PRODUCER_IMPL, producerImpl);
		//验证码文本生成器
		prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_IMPL, textproducerImpl);
		//验证码文本字符内容范围
		prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, textproducerCharString);
		//验证码文本字符间距
		prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, textproducerCharSpace);
		//验证码文本字符长度
		prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, textproducerCharLength);
		//验证码文本字体样式
		prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, textproducerFontNames);
		//验证码文本字符大小
		prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, textproducerFontSize);
		//验证码文本字符颜色
		prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, textproducerFontColor);
		//验证码噪点生成对象
		prop.setProperty(Constants.KAPTCHA_NOISE_IMPL, noiseImpl);
		//验证码噪点颜色
		prop.setProperty(Constants.KAPTCHA_NOISE_COLOR, noiseColor);
		//渲染效果
		prop.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, obscurificatorImpl);
		//验证码文本字符渲染
		prop.setProperty(Constants.KAPTCHA_WORDRENDERER_IMPL, wordRendererImpl);
		//验证码背景生成器
		prop.setProperty(Constants.KAPTCHA_BACKGROUND_IMPL, backgroundImpl);
		//验证码背景颜色渐进起始
		prop.setProperty(Constants.KAPTCHA_BACKGROUND_CLR_FROM, backgroundClearFrom);
		//验证码背景颜色渐进结束
		prop.setProperty(Constants.KAPTCHA_BACKGROUND_CLR_TO, backgroundClearTo);
		//验证码图片宽度
		prop.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, imageWidth);
		//验证码图片高度
		prop.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, imageHeight);

		return prop;
	}

	@Bean(name = "codeProducer")
	public Producer codeProducer(@Qualifier("codeProperties") Properties codeProperties) {
		//初始化配置
		Config config = new Config(codeProperties);

		//生成者
		Producer codeProducer = config.getProducerImpl();

		return codeProducer;
	}

	/**
	 * 生成验证码图片的Base64编码
	 */
	public static String[] makeBase64Code(Producer producer) {
		//生成的验证码结果
		String[] result = {"", ""};

		// 图片缓存
		ImageIO.setUseCache(false);

		// 验证码
		String code = producer.createText();
		log.info("验证码:{}",code);
		// 验证码图片缓存
		BufferedImage bi = producer.createImage(code);

		// 字节组流
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			//保存图片
			ImageIO.write(bi, "jpg", out);

			//对图片进行base64编码
			byte[] data = out.toByteArray();
			StringBuffer imageBuffer = new StringBuffer();

			BASE64Encoder encoder = new BASE64Encoder();
			imageBuffer.append(encoder.encodeBuffer(data));

			if (imageBuffer != null) {
				//将字符拼接在前面和append方法相反
				imageBuffer.insert(0, "data:image/jpeg;base64,");
				result[0] = imageBuffer.toString().replaceAll("\r|\n","");
				result[1] = code;
			}
		} catch (IOException e) {
			log.error("生成验证码出现异常:" + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
}