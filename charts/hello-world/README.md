# run manually before deploying:
kubectl create secret -n default docker-registry dockerhub-secret \
  --docker-server=docker.io \
  --docker-username=hody00 \
  --docker-password="dckr_pat_***" \
  --docker-email="user@gmail.com" \
  --dry-run=client -o yaml > secret.yaml

# secret format:
```yaml
apiVersion: v1
data:
  .dockerconfigjson: secret
kind: Secret
metadata:
  name: dockerhub-secret
  namespace: default
type: kubernetes.io/dockerconfigjson
```
