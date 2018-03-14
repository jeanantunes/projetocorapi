/**
 * SendEmail
 * API para envio de e-mail
 *
 * OpenAPI spec version: 1.0
 * Contact: arquiteteturati@odontoprev.com.br
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package br.com.odontoprev.portal.corretor.serviceEmail.model;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModelProperty;


/**
 * Attachment
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-26T13:41:47.810-03:00")
public class Attachment   {
  @SerializedName("pathAttachment")
  private String pathAttachment = null;

  @SerializedName("fileNameAttachment")
  private String fileNameAttachment = null;

  @SerializedName("contentAttachmentBase64")
  private String contentAttachmentBase64 = null;

  public Attachment pathAttachment(String pathAttachment) {
    this.pathAttachment = pathAttachment;
    return this;
  }

   /**
   * Caminho absoluto do arquivo, modo antigo
   * @return pathAttachment
  **/
  @ApiModelProperty(example = "null", value = "Caminho absoluto do arquivo, modo antigo")
  public String getPathAttachment() {
    return pathAttachment;
  }

  public void setPathAttachment(String pathAttachment) {
    this.pathAttachment = pathAttachment;
  }

  public Attachment fileNameAttachment(String fileNameAttachment) {
    this.fileNameAttachment = fileNameAttachment;
    return this;
  }

   /**
   * Nome do arquivo, modo novo
   * @return fileNameAttachment
  **/
  @ApiModelProperty(example = "null", value = "Nome do arquivo, modo novo")
  public String getFileNameAttachment() {
    return fileNameAttachment;
  }

  public void setFileNameAttachment(String fileNameAttachment) {
    this.fileNameAttachment = fileNameAttachment;
  }

  public Attachment contentAttachmentBase64(String contentAttachmentBase64) {
    this.contentAttachmentBase64 = contentAttachmentBase64;
    return this;
  }

   /**
   * Conte�do do arquivo convertido para Base64, modo novo
   * @return contentAttachmentBase64
  **/
  @ApiModelProperty(example = "null", value = "Conte�do do arquivo convertido para Base64, modo novo")
  public String getContentAttachmentBase64() {
    return contentAttachmentBase64;
  }

  public void setContentAttachmentBase64(String contentAttachmentBase64) {
    this.contentAttachmentBase64 = contentAttachmentBase64;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Attachment attachment = (Attachment) o;
    return Objects.equals(this.pathAttachment, attachment.pathAttachment) &&
        Objects.equals(this.fileNameAttachment, attachment.fileNameAttachment) &&
        Objects.equals(this.contentAttachmentBase64, attachment.contentAttachmentBase64);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pathAttachment, fileNameAttachment, contentAttachmentBase64);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Attachment {\n");
    
    sb.append("    pathAttachment: ").append(toIndentedString(pathAttachment)).append("\n");
    sb.append("    fileNameAttachment: ").append(toIndentedString(fileNameAttachment)).append("\n");
    sb.append("    contentAttachmentBase64: ").append(toIndentedString(contentAttachmentBase64)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

