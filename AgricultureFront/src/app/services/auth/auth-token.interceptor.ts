import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable()
export class AuthTokenInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  private isApiRequest(url: string): boolean {
    return (
      url.startsWith('/api/') ||
      url.startsWith('/user/api/') ||
      url.startsWith('/user/') ||
      url.startsWith('/forums/') ||
      url.startsWith('/livraison/') ||
      url.startsWith('/osrm/') ||
      url.startsWith('/Vente/') ||
      url.startsWith('/formation/') ||
      url.startsWith('/explorer/') ||
      url.startsWith('/evenement/') ||
      url.startsWith('/support/') ||
      url.startsWith('/reclamations/') ||
      url.startsWith('/pret/') ||
      url.startsWith('/inventaires/') ||
      url.startsWith('/assistance/') ||
      url.startsWith('/paiement/')
    );
  }

  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = this.authService.getToken();
    let finalUrl = req.url
      .replace('http://localhost:8089', '')
      .replace('http://localhost:8095', '');

    const authReq = this.isApiRequest(finalUrl) && token
      ? req.clone({
          url: finalUrl,
          setHeaders: { Authorization: `Bearer ${token}` }
        })
      : finalUrl !== req.url
        ? req.clone({ url: finalUrl })
        : req;

    return next.handle(authReq);
  }
}
