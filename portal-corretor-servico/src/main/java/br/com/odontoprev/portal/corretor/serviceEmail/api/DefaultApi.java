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


package br.com.odontoprev.portal.corretor.serviceEmail.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.odontoprev.portal.corretor.serviceEmail.ApiCallback;
import br.com.odontoprev.portal.corretor.serviceEmail.ApiClient;
import br.com.odontoprev.portal.corretor.serviceEmail.ApiException;
import br.com.odontoprev.portal.corretor.serviceEmail.ApiResponse;
import br.com.odontoprev.portal.corretor.serviceEmail.Configuration;
import br.com.odontoprev.portal.corretor.serviceEmail.Pair;
import br.com.odontoprev.portal.corretor.serviceEmail.ProgressRequestBody;
import br.com.odontoprev.portal.corretor.serviceEmail.ProgressResponseBody;
import br.com.odontoprev.portal.corretor.serviceEmail.model.RequestEmail;

public class DefaultApi {
    private ApiClient apiClient;

    public DefaultApi() {
        this(Configuration.getDefaultApiClient());
    }

    public DefaultApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /* Build call for sendEmail */
    private com.squareup.okhttp.Call sendEmailCall(RequestEmail body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;
        

        // create path and map variables
        String localVarPath = "/send".replaceAll("\\{format\\}","json");

        List<Pair> localVarQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    /**
     * sendEmail
     * Met�do respons�vel por enviar e-mail
     * @param body  (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void sendEmail(RequestEmail body) throws ApiException {
        sendEmailWithHttpInfo(body);
    }

    /**
     * sendEmail
     * Met�do respons�vel por enviar e-mail
     * @param body  (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> sendEmailWithHttpInfo(RequestEmail body) throws ApiException {
        com.squareup.okhttp.Call call = sendEmailCall(body, null, null);
        return apiClient.execute(call);
    }

    /**
     * sendEmail (asynchronously)
     * Met�do respons�vel por enviar e-mail
     * @param body  (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call sendEmailAsync(RequestEmail body, final ApiCallback<Void> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = sendEmailCall(body, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);
        return call;
    }
}
