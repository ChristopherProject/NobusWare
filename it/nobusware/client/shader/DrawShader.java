package it.nobusware.client.shader;

public class DrawShader {
	
    public static ShaderUtilsShader nazi = new ShaderUtilsShader(Shaders.nazi);
    public static ShaderUtilsShader Custom = null;
    
	public static void Reich() {
		DrawShader.renderShader(nazi, false);
	}

    public static void renderShader(ShaderUtilsShader shader, boolean DefaultUniforms) {
        try {
            if (Custom != null) {
                shader = Custom;
            }
            shader.renderFirst();
            shader.addDefaultUniforms(DefaultUniforms);
            shader.renderSecond();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

