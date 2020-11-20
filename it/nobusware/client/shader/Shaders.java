package it.nobusware.client.shader;

public class Shaders {

	public static String nazi = "#ifdef GL_ES\n" + 
			"precision highp float;\n" + 
			"#endif\n" + 
			"\n" + 
			"uniform vec2 resolution;\n" + 
			"uniform float time;\n" + 
			"\n" + 
			"#define TAU 6.28318530718\n" + 
			"\n" + 
			"\n" + 
			"const vec3 BackColor	= vec3(3.0, 0.3, 0.0);\n" + 
			"const vec3 CloudColor	= vec3(0.18,0.30,-1.087);\n" + 
			"\n" + 
			"\n" + 
			"float Func(float pX)\n" + 
			"{\n" + 
			"	return 0.6*(0.5*sin(0.1*pX) + 0.5*sin(0.553*pX) + 0.7*sin(1.2*pX));\n" + 
			"}\n" + 
			"\n" + 
			"\n" + 
			"float FuncR(float pX)\n" + 
			"{\n" + 
			"	return 0.5 + 0.25*(1.0 + sin(mod(40.0*pX, TAU)));\n" + 
			"}\n" + 
			"\n" + 
			"\n" + 
			"float Layer(vec2 pQ, float pT)\n" + 
			"{\n" + 
			"	vec2 Qt = 3.5*pQ;\n" + 
			"	pT *= 0.5;\n" + 
			"	Qt.x += pT;\n" + 
			"\n" + 
			"	float Xi = floor(Qt.x);\n" + 
			"	float Xf = Qt.x - Xi -0.5;\n" + 
			"\n" + 
			"	vec2 C;\n" + 
			"	float Yi;\n" + 
			"	float D = 1.0 - step(Qt.y,  Func(Qt.x));\n" + 
			"\n" + 
			"	\n" + 
			"	Yi = Func(Xi + 0.5);\n" + 
			"	C = vec2(Xf, Qt.y - Yi ); \n" + 
			"	D =  min(D, length(C) - FuncR(Xi+ pT/80.0));\n" + 
			"\n" + 
			"	Yi = Func(Xi+1.0 + 0.5);\n" + 
			"	C = vec2(Xf-1.0, Qt.y - Yi ); \n" + 
			"	D =  min(D, length(C) - FuncR(Xi+1.0+ pT/80.0));\n" + 
			"\n" + 
			"\n" + 
			"	Yi = Func(Xi-1.0 + 0.5);\n" + 
			"	C = vec2(Xf+1.0, Qt.y - Yi ); \n" + 
			"	D =  min(D, length(C) - FuncR(Xi-1.0+ pT/80.0));\n" + 
			"\n" + 
			"	return min(1.0, D);\n" + 
			"}\n" + 
			"\n" + 
			"\n" + 
			"void main(void){\n" + 
			"	vec2 uv = 1.2*(2.0*gl_FragCoord.xy - resolution.xy) / resolution.y;\n" + 
			"	\n" + 
			"	float workTime = time;\n" + 
			"    	if( workTime < 1000.0 )\n" + 
			"   	{\n" + 
			"	    workTime += 1000.0;\n" + 
			"    	}	\n" + 
			"	\n" + 
			"	vec3 Color= BackColor;\n" + 
			"\n" + 
			"	for(float J=-0.27; J<=1.27; J+=0.2)\n" + 
			"	{\n" + 
			"		\n" + 
			"		float Lt =  workTime*2.7*(0.5  + 2.0*J)*(1.0 + 0.1*sin(226.0*J)) + 17.0*J;\n" + 
			"		vec2 Lp = vec2(0.0, 0.3+1.5*( J - 0.5));\n" + 
			"		float L = Layer(uv + Lp, Lt);\n" + 
			"\n" + 
			"		\n" + 
			"		float Blur = 4.0*(0.5*abs(2.0 - 5.0*J))/(11.0 - 5.0*J);\n" + 
			"\n" + 
			"		float V = mix( 0.0, 1.0, 1.0 - smoothstep( 0.0, 0.01 +0.2*Blur, L ) );\n" + 
			"		vec3 Lc=  mix( CloudColor, vec3(1.0), J);\n" + 
			"\n" + 
			"		Color =mix(Color, Lc,  V);\n" + 
			"	}\n" + 
			"	gl_FragColor = vec4(1.-Color,1.);\n" + 
			"}";
}
