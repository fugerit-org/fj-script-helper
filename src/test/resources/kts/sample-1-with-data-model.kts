import org.fugerit.java.doc.base.kotlin.dsl.dslDoc

dslDoc {
    val docTitle = attStr( data, "docTitle" )
    meta {
        info( ( docTitle ) ).name( "doc-title" )
    }
    body {
        h( docTitle ).headLevel( 1 )
    }
}