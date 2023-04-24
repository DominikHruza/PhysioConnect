import { JwtPayload } from 'jwt-decode';
import { UserRole } from './user.model';

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  accessToken: string;
  refreshToken: string;
}

export interface PhysioConnectJwtToken extends JwtPayload {
  sub_id: number,
  roles: UserRole[]
}

