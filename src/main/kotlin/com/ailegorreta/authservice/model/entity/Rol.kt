/* Copyright (c) 2023, LegoSoft Soluciones, S.C.
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are not permitted.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
* ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
* LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
* SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
* INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
* CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE.
*
*  Rol.kt
*
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
*/
package com.ailegorreta.authservice.model.entity

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import org.springframework.data.neo4j.core.schema.*
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.security.core.GrantedAuthority
import java.time.LocalDateTime

/**
 * Entity Role. One Role has several Permits.
 *
 * A group of Roles from a Profile.
 * One role can be a member of several Profiles.
 *
 * We use ElementId instead of Long type in order to keep out the Repository warning from SDN Neo4j and for
 * future Neo4j version gor generated values or in a future version generate UIID types
 *
 * @author rlh
 * @project : auth-service
 * @date September 2023
 *
 */
@Node("Rol")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
data class Rol (@Id @GeneratedValue(GeneratedValue.InternalIdGenerator::class)
                @JsonProperty("id")                    var id: String ,
                @JsonProperty("idRol")
                @Property(name = "idRol") 			   var idRol: Long,
                @JsonProperty("nombre")
                @Property(name = "nombre") 			   var nombre: String,
                @JsonProperty("activo")
                @Property(name = "activo")	           var activo: Boolean,
                @JsonProperty("usuarioModificacion")
                @Property(name = "usuarioModificacion") var usuarioModificacion: String,
                @LastModifiedDate
                @JsonProperty("fechaModificacion")
                @Property(name = "fechaModificacion")   var fechaModificacion: LocalDateTime,
                @JsonProperty("facultades")
                @Relationship(type = "TIENE_FACULTAD", direction = Relationship.Direction.OUTGOING)
                                                        var facultades: LinkedHashSet<Facultad>?): GrantedAuthority {

    @JsonProperty("authority")
    override fun getAuthority() = "ROLE_" + nombre.uppercase()
    // ^ See how this security make difference between Roles & Granted Authorities:
    // https://www.baeldung.com/spring-security-granted-authority-vs-role

    @JsonProperty("authority")
    fun setAuthority(value: String) {}
}
