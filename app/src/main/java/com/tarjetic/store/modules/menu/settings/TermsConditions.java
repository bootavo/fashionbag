package com.tarjetic.store.modules.menu.settings;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.tarjetic.store.R;
import com.tarjetic.store.utilities.BaseActivity;

/**
 * Created by bootavo on 12/12/2017.
 */

public class TermsConditions extends BaseActivity{

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.wv_terms_conditions) WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);
        initButterKnife();
        init();
        eventUI();

    }

    public void init(){
        setTitle(null);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mWebView.getSettings().setJavaScriptEnabled(true);

        String customHtml = "<body style='padding: 12px;'>\n" +
                "\n" +
                "\t<h2 align='center'> TÉRMINOS Y CONDICIONES </h2>\n" +
                "\n" +
                "\t<p align='justify'>\n" +
                "<p>Las presentes Condiciones Generales, regulan el uso de la aplicación Tarjetic (en adelante “Tarjetic”), de propiedad de la Empresa Tarjetic con RUC: 10478669599 con nombre comercial <strong>Tarjetic</strong> (en adelante “Tarjetic”), una sociedad constituida y regida bajo la legislación vigente de la República del Perú.</p>\n" +
                "\n" +
                "      <p>La utilización de los servicios dispuestos en el Portal le atribuye al visitante o internauta la condición de Usuario del Portal (en adelante, “Usuario”) e implica su aceptación plena y sin reservas de todas y cada una de las disposiciones incluidas en estas Condiciones Generales en la versión publicada por Tarjetic, en el momento mismo en que el Usuario acceda al Portal y/o utiliza los servicios dispuestos en el mismo.</p>\n" +
                "\n" +
                "      <p>Las utilizaciones de ciertos servicios ofrecidos a los Usuarios a través del Portal se encuentran sometidos, además de a las presentes Condiciones Generales, a avisos, notificaciones e instrucciones propias (en adelante “Condiciones Particulares”) que, según los casos, sustituyen, completan y/o modifican las presentes Condiciones Generales. Las Condiciones Particulares serán debidamente comunicadas a los Usuarios por medio del Portal.</p>\n" +
                "\n" +
                "      <p>Tarjetic se reserva el derecho de modificar los términos de las presentes Condiciones Generales, así como de las Condiciones Particulares. En tal caso, Tarjetic avisará con suficiente antelación a los Usuarios registrados sobre cualquier modificación que sobre las Condiciones Generales y/o Condiciones Particulares se produzca y procederá a publicarlas en su nueva versión, sustituyendo de forma automática a las anteriores. El acceso al Portal o utilización posterior del Servicio por parte de los Usuarios tras una modificación de las Condiciones Generales y/o Condiciones Particulares supondrá la aceptación inequívoca de las mismas.</p>\n" +
                "\n" +
                "      <p>Al acceder y utilizar el sitio web de Tarjetic usted expresamente reconoce y acepta los siguientes Términos y Condiciones. Si no acepta estos Términos y Condiciones, por favor no utilice el sitio web de Tarjetic ni descargue material alguno.</p>\n" +
                "\n" +
                "      <p>Tarjetic  se reserva el derecho de modificar o suspender, temporal o permanentemente, en cualquier momento y sin previo aviso, el Sitio web o materiales publicados en los mismos (todo o parte de ellos) así como los productos descritos en ellos. Sin que ello genere derecho a reclamo o indemnización alguna a favor del usuario.</p>\n" +
                "\n" +
                "      <p>Del mismo modo, Tarjetic se reserva el derecho de modificar estos Términos y Condiciones, así como el diseño, la presentación o configuración, los requisitos de registro o uso en el Sitio web, en cualquier momento y sin previo aviso ni comunicación alguna, sin que ello genere derecho a reclamo o indemnización alguna a favor del usuario. Convenimos que es su responsabilidad vigilar los cambios y actualizaciones de forma periódica.</p>\n" +
                "\n" +
                "      <p><strong>Propiedad Intelectual:</strong></p>\n" +
                "\n" +
                "      <p>Tarjetic es el titular de todos los derechos legítimos sobre el Sitio web y los materiales que en ellos se encuentren. Asimismo, Tarjetic es licenciatario de todos los nombres comerciales, marcas comerciales, marcas de servicios, marcas de productos, logotipos, nombres de dominio y otras características distintivas de las marcas contenidas en ellos. Se prohíbe cualquier copia, reproducción, modificación, publicación, carga, envío, transmisión o distribución en modo alguno, salvo indicación expresa en contrario. Tarjetic no concede derecho expreso alguno ni implícito sobre las patentes, derechos de autor, marcas comerciales o información de secretos comerciales.</p>\n" +
                "\n" +
                "      <ol>\n" +
                "        <li>\n" +
                "          <h3>SEGURIDAD Y PRIVACIDAD</h3>\n" +
                "\n" +
                "          <p>La información que usted envíe a Tarjetic a través de formularios para completar datos en el Sitio web se regirá de conformidad con el siguiente texto. En ese sentido, como usuario de este sitio web, le informamos sobre el uso que le damos a los datos que registre en el mismo, a fin que pueda decidir si quiere ingresar sus datos o no.</p>\n" +
                "\n" +
                "          <p>Tarjetic ofrece a los Usuarios del Servicio la garantía de privacidad y seguridad en las transacciones, la confidencialidad de los datos facilitados y la encriptación de los mismos mediante el sistema de Secure Socket Layer (SSL) de 256 bits. Todos los procesos de pago de transacciones a los Usuarios serán procesados mediante los terminales de las entidades financieras con plena garantía de seguridad y confidencialidad. </p>\n" +
                "\n" +
                "          <ol>\n" +
                "            <li>\n" +
                "              <p>PROTECCIÓN DE DATOS PERSONALES</p>\n" +
                "\n" +
                "              <p>Los datos recogidos a través de este sitio web serán objeto de tratamiento automatizado por parte de Tarjetic o de terceros e incorporados a los correspondientes bancos de datos o ficheros de los que Tarjetic es titular y responsable.</p>\n" +
                "\n" +
                "              <p>A través del procedimiento de Registro de Usuarios en el Portal, éstos proporcionan a Tarjetic datos de carácter personal tales como a:</p>\n" +
                "\n" +
                "              <ul>\n" +
                "                <li>Nombres y apellidos completos.</li>\n" +
                "                <li>Dirección de correo electrónico.</li>\n" +
                "                <li>Teléfonos de contacto.</li>\n" +
                "                <li>Datos de cuentas bancarias o de tarjetas de crédito, según la modalidad de pago elegida por el Usuario.</li>\n" +
                "                <li>Tipo y número de documento de identidad.</li>\n" +
                "                <li>Domicilio.</li>\n" +
                "              </ul>\n" +
                "\n" +
                "              <p>Esta enumeración es meramente enunciativa más no limitativa, por lo que todos los demás datos de carácter personal que brinde el Usuario a Tarjetic, se sujetarán también a la presente Política.</p>\n" +
                "\n" +
                "              <p>Tarjetic podría llegar a solicitar al Usuario otros datos personales, según la justificación del caso.</p>\n" +
                "\n" +
                "              <p>Estos datos nos proporcionan información que nos permiten contactar a los usuarios para resolver dudas, responder a comentarios, enviar información, publicidad y novedades que puedan interesar a nuestros usuarios.</p>\n" +
                "\n" +
                "              <p>Del mismo modo utilizamos los datos recolectados para elaborar estadísticas, para el manejo de datos a nivel interno, para poder mejorar el sitio, para conocer las preferencias de los usuarios y adaptarlo a sus gustos y necesidades; y para registros históricos.</p>\n" +
                "\n" +
                "              <p>Al facilitar sus datos personales y/o sensibles en el Sitio web, nos está comunicando que usted de forma libre, voluntaria, previa, expresa, informada e inequívoca nos está suministrando dicha información y que además se encuentra de acuerdo y brinda su consentimiento por tiempo indefinido para que dichos datos sean administrados por Tarjetic. Sus datos permanecerán almacenados en nuestros servidores o de terceros contratados por Tarjetic y serán entregados a nuestro Equipo de Soporte de ventas, quien se pondrá en contacto para asesorarle sobre nuestros productos.</p>\n" +
                "\n" +
                "              <p>Podrá revocar su consentimiento en cualquier momento, enviando un email a tarjeticstore@gmail.com</p>\n" +
                "\n" +
                "              <p>Tarjetic se compromete a no divulgar sus datos a personas extrañas o ajenas a estas, como tampoco a usarlos o aplicarlos con una finalidad distinta por la que fueron entregados o aquella que motivó su recopilación. Por lo que es obligación de Tarjetic mantener una absoluta y total reserva y confidencialidad respecto de los datos a que tenga acceso.</p>\n" +
                "\n" +
                "              <p>Tarjetic, como titular y responsable de los Bancos de Datos Personales, ha adoptado los niveles de seguridad apropiados para el resguardo de la información registrada, de acuerdo a Ley; y respeta los principios de legalidad, consentimiento, finalidad, proporcionalidad, calidad, disposición de recurso, nivel de protección adecuado, conforme a las disposiciones de la Ley N° 29733, Ley de Protección de Datos Personales, su Reglamento, la Directiva de la Seguridad de la Información y demás normas modificatorias, complementarias y conexas.</p>\n" +
                "\n" +
                "              <p>Si requiere de más información con respecto a la Ley de Protección de Datos Personales (Ley N° 29733), y conocer así sus derechos y obligaciones, puede consultar el material instructivo elaborado por la Dirección General de Protección de Datos Personales (Ministerio de Justicia y Derechos Humanos)&nbsp;aquí.</p>\n" +
                "\n" +
                "            </li>\n" +
                "            <li>\n" +
                "              <p>Cookies</p>\n" +
                "\n" +
                "              <p>Al igual que muchas compañías, utilizamos la tecnología de cookie en nuestro sitio web. Estas cookies muy bien pueden indicarnos si Usted ha visitado nuestro sitio antes o si es un visitante nuevo y que material de nuestro sitio Usted ha visto. Las cookies que utilizamos no recolectan ninguna información sobre la identidad personal ni nos proporciona forma alguna de contactarlo y las cookies no extraen información alguna de su computadora, solo nos permite identificar por la dirección IP de la persona que navega en la página. También debe saber que reunimos cierta información sobre el uso de nuestro sitio, tal como el número y frecuencia de visitantes a ciertas secciones del sitio. Estos datos sólo son utilizados en conjunto y no incluyen ninguna información sobre la identidad personal.</p>\n" +
                "\n" +
                "              <p>Vínculos electrónicos de terceros: La página web de Tarjetic puede contener vínculos electrónicos, ligas o “Hot Links”, hacia otras páginas web de Internet que son patrocinadas, manejadas y/u operadas por proveedores externos y otras organizaciones en estrecha relación con Tarjetic. Sin embargo, aun cuando el proveedor este afiliado a Tarjetic o viceversa, Tarjetic no tiene ningún tipo de control sobre estas páginas web vinculadas.</p>\n" +
                "\n" +
                "            </li>\n" +
                "          </ol>\n" +
                "        </li>\n" +
                "\n" +
                "        <li>\n" +
                "          <h3>COMPRA DE PRODUCTOS</h3>\n" +
                "\n" +
                "          <p>El Comprador que esté interesado en adquirir alguno de los productos, deberá aceptar expresamente en todos sus términos las presentes Términos y Condiciones.</p>\n" +
                "\n" +
                "          <p>Las modalidades de compra serán los siguientes:</p>\n" +
                "\n" +
                "          <ul>\n" +
                "            <li>Compra en Oficina de Tarjetic mediante efectivo o pago por POS</li>\n" +
                "            <li>Compra por deposito en cuenta Bancaria de Tarjetic</li>\n" +
                "            <li>Pago por WEB o APP mediante una pasarela de pagos con tarjetas diversas</li>\n" +
                "          </ul>\n" +
                "\n" +
                "          <p>Tarjetic informa al Comprador que, una vez realizada la adquisición o compra de un productos, por la naturaleza del mismo, no habrá derecho a retracto, pero si respetamos el proceso de devoluciones descrito en el capítulo 4.</p>\n" +
                "\n" +
                "          <ol>\n" +
                "            <li>\n" +
                "              <p>Adjudicación y Confirmación de la compra.</p>\n" +
                "\n" +
                "              <p>Realizada la adquisición, Tarjetic enviará un correo electrónico de confirmación a la dirección facilitada por el Comprador en su formulario de registro.</p>\n" +
                "\n" +
                "              <p>Esta comunicación servirá de prueba de la transacción entre Tarjetic  y Comprador. El Comprador no podrá desistir o revocar su aceptación.</p>\n" +
                "            </li>\n" +
                "\n" +
                "            <li>\n" +
                "              <p>Precio de los productos para el Comprador.</p>\n" +
                "\n" +
                "              <p>Precio de los productos son públicos en la WEB y los usuarios tendrán acceso a todos estos cuando ingresen al portal con su Usuario y Clave, así mismo cada mes se publican nuevos productos que mediante Emails y redes sociales oficiales de Tarjetic son publicados</p>\n" +
                "            </li>\n" +
                "          </ol>\n" +
                "        </li>\n" +
                "\n" +
                "        <li>\n" +
                "          <h3>POLITICAS DE CAMBIOS Y DEVOLUCIONES</h3>\n" +
                "\n" +
                "          <p>Tarjetic solo acepta cambios y/o devoluciones por problemas de falla de fábrica. Este proceso solo se podrá realizar durante los 5 días siguientes a la compra de los productos, para ello, se deberá entregar el producto en sus condiciones originales (incluyendo bolsa plástica original, etiquetas, producto sin uso y comprobante de compra) a fin de reembolsarle su dinero o le sirva de crédito para sus siguientes compras.&nbsp;</p>\n" +
                "\n" +
                "          <p>Si se encuentra fuera de Lima. Solo deje su producto en cualquier agencia de OLVA Courier, pagando el envío a la oficina Tarjetic PERU en Lima / San Isidro. Las coordinaciones de envío se deben realizar con el Equipo de Asesoras en horario de oficina. La devolución vía correo postal, dependiendo del servicio, puede tomar entre 7 a 10 días laborables en llegar a nuestras oficinas y otros 2 días en ser evaluado. Luego de aprobado el reembolso, considerar el tiempo de 15 días laborables para que se complete totalmente la devolución.</p>\n" +
                "        </li>\n" +
                "\n" +
                "        <li>\n" +
                "          <h3>SATISFACCIÓN GARANTIZADA</h3>\n" +
                "\n" +
                "          <p>Con el fin de ofrecer el mejor servicio a sus clientes, Tarjetic se compromete en ofrecer la satisfacción garantizada de sus compras.</p>\n" +
                "\n" +
                "          <p>Se realizará el cambio o devolución del monto pagado si el cliente no está satisfecho con el producto adquirido.</p>\n" +
                "\n" +
                "          <p>Condiciones:</p>\n" +
                "\n" +
                "          <ul>\n" +
                "            <li>El producto debe contar con todos sus empaques originales (bolsas, etiquetas) en perfectas condiciones.</li>\n" +
                "            <li>Al momento de realizar el cambio se debe presentar la boleta o factura respectiva.</li>\n" +
                "          </ul>\n" +
                "        </li>\n" +
                "\n" +
                "        <li>\n" +
                "          <h3>USTED RECONOCE Y ACEPTA QUE</h3>\n" +
                "\n" +
                "          <ul>\n" +
                "            <li>Ha leído y acepta los términos y condiciones de uso descritos anteriormente.</li>\n" +
                "            <li>El uso de los Sitios web y de los materiales publicados en los mismos corren por la cuenta y riesgo del usuario.</li>\n" +
                "            <li>Todo material descargado u obtenido de otro modo a través del sitio web corre por la cuenta y riesgo del usuario, quien será el único responsable de los daños que pueda sufrir su sistema informático u otro dispositivo o la pérdida de datos que derive de la descarga de ese material.</li>\n" +
                "            <li>En ningún caso ni Tarjetic, ni sus empleados, ni funcionarios será responsable de daño alguno derivado del uso de los Sitios web o de los materiales, o de la imposibilidad de uso de los mismos.</li>\n" +
                "            <li>En ningún caso ni Tarjetic, ni sus empleados, ni funcionarios, responderá por daños especiales, indirectos, incidentales o de cualquier otra naturaleza que deriven de lucro cesante, pérdida de uso o pérdida de datos, emergente del o en relación con el uso o desempeño del software, documentos, la prestación o no de servicios o información disponible en el sitio web.</li>"+
                "\t</p>\n" +
                "\n" +
                "</body>";

        mWebView.loadData(customHtml, "text/html; charset=utf-8", "UTF-8");
    }

    public void initButterKnife(){
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void eventUI(){
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
