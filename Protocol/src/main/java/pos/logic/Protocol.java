package pos.logic;

public class Protocol {
    public static final String SERVER = "localhost";
    public static final int PORT = 1234;

    public static final int MEDICO_CREATE=101;
    public static final int MEDICO_READ=102;
    public static final int MEDICO_UPDATE=103;
    public static final int MEDICO_DELETE=104;
    public static final int MEDICO_FIND_ALL=105;
    public static final int MEDICO_FILTER=106;

    public static final int FARMACEUTA_CREATE=201;
    public static final int FARMACEUTA_READ=202;
    public static final int FARMACEUTA_UPDATE=203;
    public static final int FARMACEUTA_DELETE=204;
    public static final int FARMACEUTA_FIND_ALL=205;
    public static final int FARMACEUTA_FILTER=206;

    public static final int PACIENTE_CREATE=301;
    public static final int PACIENTE_READ=302;
    public static final int PACIENTE_UPDATE=303;
    public static final int PACIENTE_DELETE=304;
    public static final int PACIENTE_FIND_ALL=305;
    public static final int PACIENTE_FILTER=306;

    public static final int MEDICAMENTO_CREATE=401;
    public static final int MEDICAMENTO_READ=402;
    public static final int MEDICAMENTO_UPDATE=403;
    public static final int MEDICAMENTO_DELETE=404;
    public static final int MEDICAMENTO_FIND_ALL=405;
    public static final int MEDICAMENTO_FILTER=406;

    public static final int RECETA_CREATE=501;
    public static final int RECETA_READ=502;
    public static final int RECETA_UPDATE=503;
    public static final int RECETA_DELETE=504;
    public static final int RECETA_FIND_ALL=505;
    public static final int RECETA_FILTER=506;
    public static final int RECETA_FILTER_2=507;

    public static final int PRESCRIPCION_CREATE=550;

    public static final int USUARIO_CREATE=601;
    public static final int USUARIO_READ=602;
    public static final int USUARIO_UPDATE=603;
    public static final int USUARIO_DELETE=604;
    public static final int USUARIO_FIND_ALL=605;
    public static final int USUARIO_FILTER=606;
    public static final int USUARIO_GET_CURRENT =606;

    public static final int STATUS_NO_ERROR=0;
    public static final int STATUS_ERROR=1;

    public static final int DISCONNECT=99;
}