import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment as env } from '../../../environments/environment';
export class APIInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log(`URL:: ${env.api.serverUrl}${req.url}`)
    const apiReq = req.clone({ url: `${env.api.serverUrl}${req.url}` });
    return next.handle(apiReq);
  }
}
