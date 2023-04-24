
export class User {
  constructor(
    public id: number,
    public email: string,
    public roles: UserRole[],
    public tokenExpirationTimestamp: number,
    public accessToken: string
  ) {}
}

export enum UserRole {
  PRACTITIONER = "PRACTITIONER"
}
