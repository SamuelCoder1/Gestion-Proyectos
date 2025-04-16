export interface Project {
  id: number;
  title: string;
  description: string;
  deliveryDate: string;
  status: string;
}

export interface ProjectRequest {
  title: string;
  description: string;
  deliveryDate: string;
  status: string;
}

